package com.stone.wechat.networks

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.nio.file.Files.notExists

object CloudFireStoreFirebaseApiImpl : CloudFireStoreApi {


    override fun createUser(
        name: String,
        phone: String,
        dob: String,
        gender: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val user = hashMapOf(
            "name" to name,
            "phone" to phone,
            "dob" to dob,
            "gender" to gender,
            "password" to password
        )
        Firebase.firestore.collection("users")
            .document(phone)
            .set(user)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully added grocery")
            }
            .addOnFailureListener {
                Log.d("Failure", "Failed to add grocery")
                onFailure()

            }
    }

    override fun checkPhoneNumber(phone: String, exists: () -> Unit, notExists: () -> Unit) {
        val document = Firebase.firestore.collection("users").document(phone)
        document.addSnapshotListener { snap, error ->
            if (snap?.exists() == true) {
                exists()
            } else {
                notExists()
            }

        }


    }

    override fun login(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        val document = Firebase.firestore.collection("users").document(phone)
        document.get().addOnSuccessListener { snap ->
            if (snap.exists())
                if (snap.get("password") == password) onSuccess() else onFailure("Incorrect Password")
            else onFailure("Incorrect Phone or Password")
        }.addOnFailureListener {
            onFailure(it.localizedMessage ?: "Something wrong")
        }


    }
}