package com.stone.wechat.networks

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl.storageReference
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
            } else onFailure("Incorrect Phone or Password")
        }.addOnFailureListener {
            onFailure(it.localizedMessage ?: "Something wrong")
        }


    }


    override fun createMoment(
        userVO: UserVO,
        momentText: String,
        momentContents: List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val contentUrlList: ArrayList<String> = arrayListOf()
        if (momentContents.isNotEmpty()) {
            uploadFile(momentContents,
                onSuccess = {
                    contentUrlList.add(it)
                    if (contentUrlList.size == momentContents.size) {
                        Log.i("imageList", contentUrlList.toString())
                        val moments = hashMapOf(
                            FIRE_STORE_MOMENT_VO_USERID to userVO.userId,
                            FIRE_STORE_MOMENT_VO_USERNAME to userVO.name,
                            FIRE_STORE_MOMENT_VO_PROFILE to userVO.profile,
                            FIRE_STORE_MOMENT_VO_TIME to System.currentTimeMillis(),
                            FIRE_STORE_MOMENT_VO_ISMOVIE to momentContents.firstOrNull()?.isMovie,
                            FIRE_STORE_MOMENT_VO_CONTENT to contentUrlList,
                            FIRE_STORE_MOMENT_VO_MOMENTTEXT to momentText,
                            FIRE_STORE_MOMENT_VO_IMAGE_LIST to contentUrlList
                        )

                        Firebase.firestore.collection("moments")
                            .document()
                            .set(moments)
                            .addOnSuccessListener {
                                onSuccess("Upload Moment Successfully")
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
        } else {
            val moments = hashMapOf(
                FIRE_STORE_MOMENT_VO_USERID to userVO.userId,
                FIRE_STORE_MOMENT_VO_USERNAME to userVO.name,
                FIRE_STORE_MOMENT_VO_PROFILE to userVO.profile,
                FIRE_STORE_MOMENT_VO_TIME to System.currentTimeMillis(),
                FIRE_STORE_MOMENT_VO_ISMOVIE to momentContents.firstOrNull()?.isMovie,
                FIRE_STORE_MOMENT_VO_CONTENT to contentUrlList,
                FIRE_STORE_MOMENT_VO_MOMENTTEXT to momentText,
                FIRE_STORE_MOMENT_VO_IMAGE_LIST to contentUrlList
            )

            Firebase.firestore.collection("moments")
                .document()
                .set(moments)
                .addOnSuccessListener {
                    onSuccess("Upload Moment Successfully")
                }
                .addOnFailureListener { error ->
                    onFailure(
                        error.localizedMessage ?: "Failed to add moment to fire store."
                    )
                }
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
                            userId = data?.get("userId") as String?,
                            userName = data?.get("name") as String?,
                            time = data?.get(FIRE_STORE_MOMENT_VO_TIME) as Long?,
                            isMovie = data?.get(FIRE_STORE_MOMENT_VO_ISMOVIE) as Boolean? ?: false,
                            momentText = data?.get("momentText") as String?,
                            content = data?.get("contentList") as List<String>?,
                            imageList = data?.get("contentList") as List<String>?,
                            profileImage = data?.get("profile") as String?
                        )
                        momentList.add(momentVO)
                    }
                    onSuccess(momentList)
                }
            }
    }

    override fun getCurrentUserFromFireStore(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        Firebase.firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { snap ->
                if (snap.exists()) {
                    val user = UserVO(
                        name = snap.get(FIRE_STORE_USER_VO_NAME) as String,
                        phone = snap.get(FIRE_STORE_USER_VO_PHONE) as String,
                        password = snap.get(FIRE_STORE_USER_VO_PASSWORD) as String,
                        dob = snap.get(FIRE_STORE_USER_VO_DOB) as String,
                        gender = snap.get(FIRE_STORE_USER_VO_GENDER) as String,
                        userId = snap.get(FIRE_STORE_USER_VO_USERID) as String,
                        qrCode = snap.get(FIRE_STORE_USER_VO_QRCODE) as String,
                        profile = snap.get(FIRE_STORE_USER_VO_PROFILE) as String
                    )
                    onSuccess(user)
                } else {
                    onError("Oops something wrong")
                }
            }.addOnFailureListener {
                onError(it.localizedMessage)
            }
    }

    override fun getProfileData(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Firebase.firestore.collection("users")
            .document(userId)
            .addSnapshotListener { snap, error ->
                error?.let {
                    onFailure(error.localizedMessage ?: error.message ?: "Oops something wrong")
                } ?: run {

//                    val data = snap.data
                    if (snap != null) {
                        val user = UserVO(
                            name = snap.get(FIRE_STORE_USER_VO_NAME) as String,
                            phone = snap.get(FIRE_STORE_USER_VO_PHONE) as String,
                            password = snap.get(FIRE_STORE_USER_VO_PASSWORD) as String,
                            dob = snap.get(FIRE_STORE_USER_VO_DOB) as String,
                            gender = snap.get(FIRE_STORE_USER_VO_GENDER) as String,
                            userId = snap.get(FIRE_STORE_USER_VO_USERID) as String,
                            qrCode = snap.get(FIRE_STORE_USER_VO_QRCODE) as String,
                            profile = snap.get(FIRE_STORE_USER_VO_PROFILE) as String
                        )
                        onSuccess(user)
                    }
                }


            }
    }

    override fun updateProfileData(
        userVO: UserVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        userVO.userId?.let {

            val user = hashMapOf(
                "userId" to userVO.userId,
                "name" to userVO.name,
                "phone" to userVO.phone,
                "dob" to userVO.dob,
                "gender" to userVO.gender,
                "password" to userVO.password,
                "profile" to userVO.profile,
                "qrCode" to userVO.qrCode,
            )
            Firebase.firestore.collection("users").document(it).set(user).addOnSuccessListener {
                onSuccess("success")

            }.addOnFailureListener {
                onFailure(it.localizedMessage ?: "Update Profile Fail")
            }
        }

    }

    override fun updateProfileImage(
        image: Bitmap,
        userVO: UserVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
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

            userVO.userId?.let {

                val user = hashMapOf(
                    "userId" to userVO.userId,
                    "name" to userVO.name,
                    "phone" to userVO.phone,
                    "dob" to userVO.dob,
                    "gender" to userVO.gender,
                    "password" to userVO.password,
                    "profile" to imageUrl,
                    "qrCode" to userVO.qrCode,
                )
                Firebase.firestore.collection("users").document(it).set(user).addOnSuccessListener {
                    onSuccess("success")

                }.addOnFailureListener {
                    onFailure(it.localizedMessage ?: "Update Profile Fail")
                }
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

const val FIRE_STORE_USER_VO_NAME = "name"
const val FIRE_STORE_USER_VO_PHONE = "phone"
const val FIRE_STORE_USER_VO_PASSWORD = "password"
const val FIRE_STORE_USER_VO_GENDER = "gender"
const val FIRE_STORE_USER_VO_DOB = "dob"
const val FIRE_STORE_USER_VO_PROFILE = "profile"
const val FIRE_STORE_USER_VO_QRCODE = "qrCode"
const val FIRE_STORE_USER_VO_USERID = "userId"



const val FIRE_STORE_MOMENT_VO_USERID = "userId"
const val FIRE_STORE_MOMENT_VO_USERNAME = "name"
const val FIRE_STORE_MOMENT_VO_PROFILE = "profile"
const val FIRE_STORE_MOMENT_VO_TIME = "time"
const val FIRE_STORE_MOMENT_VO_ISMOVIE = "isMovie"
const val FIRE_STORE_MOMENT_VO_CONTENT = "contentList"
const val FIRE_STORE_MOMENT_VO_MOMENTTEXT = "momentText"
const val FIRE_STORE_MOMENT_VO_IMAGE_LIST = "imageList"