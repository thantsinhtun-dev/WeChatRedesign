package com.stone.wechat.networks

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO
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
                            profileImage = data?.get("profile") as String?,
                            likeCount = data?.get("like") as Int? ?: 0,
                            commentCount = data?.get("comment") as Int? ?: 0,
                            isLiked = data?.get("isLiked") as Boolean? ?: false,
                            isSaved = data?.get("isSaved") as Boolean? ?: false
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
                            name = snap.get(FIRE_STORE_USER_VO_NAME) as String?,
                            phone = snap.get(FIRE_STORE_USER_VO_PHONE) as String?,
                            password = snap.get(FIRE_STORE_USER_VO_PASSWORD) as String?,
                            dob = snap.get(FIRE_STORE_USER_VO_DOB) as String?,
                            gender = snap.get(FIRE_STORE_USER_VO_GENDER) as String?,
                            userId = snap.get(FIRE_STORE_USER_VO_USERID) as String?,
                            qrCode = snap.get(FIRE_STORE_USER_VO_QRCODE) as String?,
                            profile = snap.get(FIRE_STORE_USER_VO_PROFILE) as String?
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
                FIRE_STORE_USER_VO_USERID to userVO.userId,
                FIRE_STORE_USER_VO_NAME to userVO.name,
                FIRE_STORE_USER_VO_PHONE to userVO.phone,
                FIRE_STORE_USER_VO_DOB to userVO.dob,
                FIRE_STORE_USER_VO_GENDER to userVO.gender,
                FIRE_STORE_USER_VO_PASSWORD to userVO.password,
                FIRE_STORE_USER_VO_PROFILE to userVO.profile,
                FIRE_STORE_USER_VO_QRCODE to userVO.qrCode,
                FIRE_STORE_MOMENT_VO_IS_ONLINE to true,
                FIRE_STORE_MOMENT_VO_LAST_ONLINE_TIME to userVO.lastOnlineTime
            )

            Firebase.firestore.collection(FIRE_STORE_USER_COLLECTION).document(it).set(user)
                .addOnSuccessListener {
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
                    FIRE_STORE_USER_VO_USERID to userVO.userId,
                    FIRE_STORE_USER_VO_NAME to userVO.name,
                    FIRE_STORE_USER_VO_PHONE to userVO.phone,
                    FIRE_STORE_USER_VO_DOB to userVO.dob,
                    FIRE_STORE_USER_VO_GENDER to userVO.gender,
                    FIRE_STORE_USER_VO_PASSWORD to userVO.password,
                    FIRE_STORE_USER_VO_PROFILE to imageUrl,
                    FIRE_STORE_USER_VO_QRCODE to userVO.qrCode,
                    FIRE_STORE_MOMENT_VO_IS_ONLINE to true,
                    FIRE_STORE_MOMENT_VO_LAST_ONLINE_TIME to System.currentTimeMillis()
                )
                Firebase.firestore.collection("users").document(it).set(user).addOnSuccessListener {
                    onSuccess("success")

                }.addOnFailureListener {
                    onFailure(it.localizedMessage ?: "Update Profile Fail")
                }
            }

        }
    }

    override fun addContacts(
        currentUserId: String,
        addUserId: String,
        onSuccess: (List<ContactVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val user = hashMapOf<String, Any>(
            FIRE_STORE_USER_VO_USERID to currentUserId,
        )
        val addUser = hashMapOf<String, Any>(
            FIRE_STORE_USER_VO_USERID to addUserId
        )
        Firebase.firestore
            .collection(FIRE_STORE_USER_COLLECTION).document(currentUserId)
            .collection(FIRE_STORE_CONTACTS_COLLECTION)
            .document(addUserId)
            .set(addUser)
            .addOnSuccessListener {
                onSuccess(arrayListOf())
            }.addOnFailureListener { onFailure(it.localizedMessage ?: "Fail to add contact") }
        Firebase.firestore
            .collection(FIRE_STORE_USER_COLLECTION).document(addUserId)
            .collection(FIRE_STORE_CONTACTS_COLLECTION)
            .document(currentUserId)
            .set(user)
            .addOnSuccessListener {
                onSuccess(arrayListOf())
            }.addOnFailureListener { onFailure(it.localizedMessage ?: "Fail to add contact") }
    }

    override fun getAllContacts(
        currentUserId: String,
        onSuccess: (List<ContactVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Firebase.firestore.collection(FIRE_STORE_USER_COLLECTION)
            .document(currentUserId)
            .collection(FIRE_STORE_CONTACTS_COLLECTION)
            .addSnapshotListener { snap, error ->
                error?.let {
                    onFailure(error.localizedMessage ?: error.message ?: "Oops something wrong")
                } ?: run {
                    val result = snap?.documents ?: arrayListOf()
                    val contactList: MutableList<ContactVO> = mutableListOf()

                    result.map {
                        val contactUserId = it?.get(FIRE_STORE_USER_VO_USERID) as String
                        loadProfileData(contactUserId, onSuccess = { userVo ->
                            val contactVO = ContactVO(
                                contactUserId,
                                userVo.name ?: "",
                                userVo.profile ?: "",
                                isFavourite = "false",
                                onlineStatus = userVo.onlineStatus,
                                lastOnlineTime = userVo.lastOnlineTime
                            )
                            contactList.add(contactVO)
                            if (contactList.size == result.size) {
                                onSuccess(contactList)
                            }
                        }, onFailure = {

                        })

                    }
                }
            }
    }

    override fun getAllMoments(
        userId: String,
        onTapLikeCallBack: (MomentVO) -> Unit,
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Fail to load moments")
                } ?: run {
                    val momentList: MutableList<MomentVO> = mutableListOf()
                    momentList.clear()
                    val result = snap?.documents ?: arrayListOf()
                    Log.i("moment_size", result.size.toString())
                    for (document in result) {


                        ///load like
                        loadLikeCount(document.id,
                            onFailure = {
                                onFailure(it)
                            },
                            onSuccess = { userList ->

                                //check is save
                                checkSave(userId, document.id, onSuccess = { isSave ->

                                    val data = document.data
                                    val momentVO = MomentVO(
                                        momentId = document.id,
                                        userId = data?.get("userId") as String?,
                                        userName = data?.get("name") as String?,
                                        time = data?.get(FIRE_STORE_MOMENT_VO_TIME) as Long?,
                                        isMovie = data?.get(FIRE_STORE_MOMENT_VO_ISMOVIE) as Boolean?
                                            ?: false,
                                        momentText = data?.get("momentText") as String?,
                                        content = data?.get("contentList") as List<String>?,
                                        imageList = data?.get("contentList") as List<String>?,
                                        profileImage = data?.get("profile") as String?,
                                        likeCount = userList.size ?: 0,
                                        commentCount = data?.get("comment") as Int? ?: 0,
                                        isLiked = userList.contains(userId),
                                        isSaved = isSave ?: false
                                    )
                                    Log.i("moment_size_result", result.size.toString())
                                    Log.i("moment_size_moment", momentList.size.toString())

                                    val filter = momentList.filter {
                                        it.momentId == momentVO.momentId
                                    }
                                    if (filter.isNotEmpty()) {
                                        filter[0].likeCount = userList.size
                                        filter[0].isLiked = userList.contains(userId)
                                        filter[0].isSaved = isSave
                                        onTapLikeCallBack(momentVO)
//                                onSuccess(momentList)
                                    } else {
                                        momentList.add(momentVO)
                                    }
                                    if (momentList.size >= result.size) {
                                        onSuccess(momentList)
                                    }

                                }, onFailure = {

                                })
                            })

                    }

                }
            }
    }

    override fun handleLike(
        userId: String,
        isRemoveLike: Boolean,
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (isRemoveLike) {

            Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
                .document(momentId)
                .collection(FIRE_STORE_LIKE_COLLECTION)
                .document(userId)
                .delete()
        } else {
            val user = hashMapOf(
                FIRE_STORE_MOMENT_VO_USERID to userId
            )
            Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
                .document(momentId)
                .collection(FIRE_STORE_LIKE_COLLECTION)
                .document(userId)
                .set(user)
        }

    }

    override fun saveMoment(
        userId: String,
        momentId: String,
        isSaveMoment: Boolean,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (isSaveMoment) {
            val user = hashMapOf(
                FIRE_STORE_MOMENT_VO_USERID to userId
            )
            Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
                .document(momentId)
                .collection(FIRE_STORE_SAVE_COLLECTION)
                .document(userId)
                .set(user)
        } else {
            Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
                .document(momentId)
                .collection(FIRE_STORE_SAVE_COLLECTION)
                .document(userId)
                .delete()
        }
    }

    override fun changeOnlineStatus(
        userId: String,
        isOnline: Boolean,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        loadProfileData(userId, onSuccess = { userVO ->
            userVO.userId?.let {

                val user = hashMapOf(
                    FIRE_STORE_USER_VO_USERID to userVO.userId,
                    FIRE_STORE_USER_VO_NAME to userVO.name,
                    FIRE_STORE_USER_VO_PHONE to userVO.phone,
                    FIRE_STORE_USER_VO_DOB to userVO.dob,
                    FIRE_STORE_USER_VO_GENDER to userVO.gender,
                    FIRE_STORE_USER_VO_PASSWORD to userVO.password,
                    FIRE_STORE_USER_VO_PROFILE to userVO.profile,
                    FIRE_STORE_USER_VO_QRCODE to userVO.qrCode,
                    FIRE_STORE_MOMENT_VO_IS_ONLINE to isOnline,
                    FIRE_STORE_MOMENT_VO_LAST_ONLINE_TIME to System.currentTimeMillis()
                )
                Firebase.firestore.collection("users").document(it).set(user).addOnSuccessListener {
                    onSuccess("success")

                }.addOnFailureListener {
                    onFailure(it.localizedMessage ?: "Update Profile Fail")
                }
            }
        }, onFailure = {

        })


    }

    private fun loadLikeCount(
        momentId: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
            .document(momentId)
            .collection(FIRE_STORE_LIKE_COLLECTION)
            .addSnapshotListener { snap, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "")
                } ?: run {
                    val snapCount = snap?.documents?.size
                    Log.i("user_id", snap?.documents.toString())
                    Log.i("user_id", snapCount.toString())
                    if (snapCount == 0) {
                        onSuccess(arrayListOf())
                    }
//                    Log.i(momentid)
                    val list: MutableList<String> = mutableListOf()
                    val result = snap?.documents ?: arrayListOf()
                    for (document in result) {
                        val data = document.data
                        val user = data?.get(FIRE_STORE_MOMENT_VO_USERID) as String?
                        list.add(user.toString())
                        if (list.size == result.size) {
                            onSuccess(list)
                        }
                    }
                }
            }


    }

    private fun checkSave(
        userId: String,
        momentId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Firebase.firestore.collection(FIRE_STORE_MOMENTS_COLLECTION)
            .document(momentId)
            .collection(FIRE_STORE_SAVE_COLLECTION)
            .addSnapshotListener { snap, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "")
                } ?: run {
                    val documents = snap?.documents.toString()
                    if (documents.contains(userId)) {
                        onSuccess(true)
                    } else {
                        onSuccess(false)
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
                    onFailure(
                        error.localizedMessage ?: "Upload video to firebase storage failed."
                    )
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

    private fun loadProfileData(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Firebase.firestore.collection(FIRE_STORE_USER_COLLECTION)
            .document(userId)
            .get()
            .addOnSuccessListener {
                val snap = it.data
                if (snap != null) {
                    val user = UserVO(
                        name = snap[FIRE_STORE_USER_VO_NAME] as String,
                        phone = snap[FIRE_STORE_USER_VO_PHONE] as String,
                        password = snap[FIRE_STORE_USER_VO_PASSWORD] as String,
                        dob = snap[FIRE_STORE_USER_VO_DOB] as String,
                        gender = snap[FIRE_STORE_USER_VO_GENDER] as String,
                        userId = snap[FIRE_STORE_USER_VO_USERID] as String,
                        qrCode = snap[FIRE_STORE_USER_VO_QRCODE] as String,
                        profile = snap[FIRE_STORE_USER_VO_PROFILE] as String,
                        onlineStatus = snap[FIRE_STORE_MOMENT_VO_IS_ONLINE] as Boolean,
                        lastOnlineTime = snap[FIRE_STORE_MOMENT_VO_LAST_ONLINE_TIME]  as Long,
                    )
                    onSuccess(user)
                }
            }.addOnFailureListener {
                onFailure(it.localizedMessage ?: "load profile fail")
            }
    }
}

const val FIRE_STORE_USER_COLLECTION = "users"
const val FIRE_STORE_CONTACTS_COLLECTION = "contacts"
const val FIRE_STORE_MOMENTS_COLLECTION = "moments"
const val FIRE_STORE_LIKE_COLLECTION = "likes"
const val FIRE_STORE_SAVE_COLLECTION = "saves"


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
const val FIRE_STORE_MOMENT_VO_IS_ONLINE = "isOnline"
const val FIRE_STORE_MOMENT_VO_LAST_ONLINE_TIME = "lastOnlineTime"