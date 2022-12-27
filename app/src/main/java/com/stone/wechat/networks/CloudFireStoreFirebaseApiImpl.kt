package com.stone.wechat.networks

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

object CloudFireStoreFirebaseApiImpl : CloudFireStoreApi {

    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    override fun createUser(
        userId: String,
        name: String,
        phone: String,
        dob: String,
        gender: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val user = hashMapOf(
            "userId" to userId,
            "name" to name,
            "phone" to phone,
            "dob" to dob,
            "gender" to gender,
            "password" to password,
            "profile" to "",
            "qrCode" to System.currentTimeMillis().toString().plus(phone),
            )
        Firebase.firestore.collection("users").document(userId).set(user).addOnSuccessListener {
            onSuccess()

        }.addOnFailureListener {
            onFailure(it.localizedMessage ?: "Oops somethings wrong, plz try again later")
        }
    }

    override fun checkPhoneNumber(phone: String, exists: () -> Unit, notExists: () -> Unit) {
        Firebase.firestore.collection("users").document(phone)
            .get().addOnSuccessListener { snap ->
                if (snap?.exists() == true) {
                    exists()
                } else {
                    notExists()
                }

            }


    }

    override fun login(
        phone: String, password: String, onSuccess: () -> Unit, onFailure: (message: String) -> Unit
    ) {
        val document = Firebase.firestore.collection("users").document(phone)
        document.get().addOnSuccessListener { snap ->
            if (snap.exists()) {
                if (snap.get("password") == password) onSuccess() else onFailure("Incorrect Password")
            }
            else onFailure("Incorrect Phone or Password")
        }.addOnFailureListener {
            onFailure(it.localizedMessage ?: "Something wrong")
        }


    }


    override fun createMoment(
        momentText: String,
        momentContents: List<MomentFileVO>,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        val contentUrlList: ArrayList<String> = arrayListOf()
        if (momentContents.isNotEmpty()) {
            uploadFile(momentContents,
                onSuccess = {
                    contentUrlList.add(it)
                    if (contentUrlList.size == momentContents.size) {
                        Log.i("imageList", contentUrlList.toString())
                        val moments = hashMapOf(
                            "momentText" to momentText,
                            "contentList" to contentUrlList,
                            "isMovie" to momentContents.firstOrNull()?.isMovie,
                            "name" to "Test",
                            "time" to System.currentTimeMillis()
                        )

                        Firebase.firestore.collection("moments")
                            .document()
                            .set(moments)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { error ->
                                onFailure(
                                    error.localizedMessage ?: "Failed to add moment to fire store."
                                )
                            }
                    }
                },
                onFailure = {
                    onFailure(it)
                })
        }
    }

    override fun getMoments(
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        Firebase.firestore.collection("moments")
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val momentList: MutableList<MomentVO> = mutableListOf()
                    val result = snap?.documents ?: arrayListOf()
                    for (document in result) {
                        val data = document.data
                        val momentVO = MomentVO(
                            userId = "",
                            userName = data?.get("name") as String,
                            time = data["time"] as Long,
                            isMovie = data["isMovie"] as Boolean,
                            momentText = data["momentText"] as String,
                            content = data["contentList"] as List<String>?,
                            imageList = data["contentList"] as List<String>?,
                            profileImage = ""
                        )
                        momentList.add(momentVO)
                    }
                    onSuccess(momentList)
                }
            }
    }


    private fun uploadFile(
        momentFileVO: List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        momentFileVO.forEach {
            if (it.isMovie) {
                val videoRef = storageReference.child("videos/${UUID.randomUUID()}")
                val uploadTask = it.moviePath?.let { movie -> videoRef.putFile(movie) }
                uploadTask?.addOnFailureListener { error ->
                    onFailure(error.localizedMessage ?: "Upload video to firebase storage failed.")
                }?.addOnSuccessListener { taskSnapshot ->

                }
                val urlTask = uploadTask?.continueWithTask {
                    return@continueWithTask videoRef.downloadUrl
                }?.addOnCompleteListener { task ->
                    val movieUrl = task.result?.toString()
                    onSuccess(movieUrl.toString())
                }
            } else {
                val baos = ByteArrayOutputStream()
                it.content.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                val imageRef = storageReference.child("images/${UUID.randomUUID()}")
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    onFailure(it.message ?: "Upload image to firebase storage failed.")
                }.addOnSuccessListener { taskSnapshot ->

                }
                val urlTask = uploadTask.continueWithTask {
                    return@continueWithTask imageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    val imageUrl = task.result?.toString()
                    onSuccess(imageUrl.toString())
                }
            }
        }


    }
}