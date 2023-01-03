package com.stone.wechat.networks.realtime

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.data.vos.MessagesVO

object FirebaseRealTimeDBImpl : FirebaseRealTimeDB {
    override val database: DatabaseReference = Firebase.database.reference

    override fun getAllChatsMessages(
        senderId: String,
        onSuccess: (List<ChatHistoryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child(FIREBASE_MESSAGE_COLLECTION)
            .child(senderId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatHistory = arrayListOf<ChatHistoryVO>()
                    snapshot.children.forEach { dataSnapshot ->
                        val chatHistoryVO = ChatHistoryVO()
                        chatHistoryVO.userId = dataSnapshot.key.toString()
                        val child = dataSnapshot.children.last()
                        child.getValue(MessagesVO::class.java)?.let {
                            chatHistoryVO.messages = it
                        }
                        chatHistory.add(chatHistoryVO)
                    }
                    onSuccess(chatHistory)
                }
            })
    }


    override fun getChatsMessagesWithContactId(
        currentUserId: String,
        contactUserId: String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child(FIREBASE_MESSAGE_COLLECTION)
            .child(currentUserId)
            .child(contactUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatHistory = arrayListOf<MessagesVO>()
                    snapshot.children.forEach { dataSnapshot ->
                        dataSnapshot.getValue(MessagesVO::class.java)?.let {
                            chatHistory.add(it)
                        }
                    }
                    onSuccess(chatHistory)
                }
            })
    }

    override fun sendMessages(
        currentUserId: String,
        contactUserId: String,
        messages: MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child(FIREBASE_MESSAGE_COLLECTION)
            .child(currentUserId)
            .child(contactUserId)
            .child(System.currentTimeMillis().toString())
            .setValue(messages)
        database.child(FIREBASE_MESSAGE_COLLECTION)
            .child(contactUserId)
            .child(currentUserId)
            .child(System.currentTimeMillis().toString())
            .setValue(messages)
    }

    override fun createGroup(
        currentUserId: String,
        groupName: String,
        groupPhoto: String,
        memberList: List<String>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val allMember:MutableList<String> = mutableListOf()
        allMember.addAll(memberList)
        allMember.add(currentUserId)

        val groupId = System.currentTimeMillis().toString()

            val groupVO = GroupVO(groupId, groupName, groupPhoto, allMember)
            database.child(FIREBASE_GROUP_COLLECTION)
                .child(groupId)
                .setValue(groupVO)
                .addOnSuccessListener {

                }.addOnFailureListener {
                    onFailure(it.localizedMessage ?: "Fail to create group")
                }.continueWith {

                }



    }

    override fun getAllGroups(
        currentUserId: String,
        onSuccess: (List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child(FIREBASE_GROUP_COLLECTION)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("groups",snapshot.value.toString())
                    val groupList = arrayListOf<GroupVO>()
                    snapshot.children.forEach { dataSnapshot ->
                        dataSnapshot.getValue(GroupVO::class.java)?.let { vo->
                            val tt = vo.memberList.map {
                                it == currentUserId
                            }
                            if (tt.isNotEmpty()) {
                                groupList.add(vo)
                            }
                        }
                    }
                    onSuccess(groupList)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
            })
    }

    override fun sendGroupMessages(
        groupId: String,
        messages: MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val ref = database.child(FIREBASE_GROUP_COLLECTION)
            .child(groupId)
            .child(FIREBASE_MESSAGE_COLLECTION)
            .child(System.currentTimeMillis().toString())
            .setValue(messages)

        Log.i("ref",ref.toString())

    }

    override fun getAllGroupMessages(
        groupId: String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child(FIREBASE_GROUP_COLLECTION)
            .child(groupId)
            .child(FIREBASE_MESSAGE_COLLECTION)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatHistory = arrayListOf<MessagesVO>()
                    snapshot.children.forEach { dataSnapshot ->
                        dataSnapshot.getValue(MessagesVO::class.java)?.let {
                            chatHistory.add(it)
                        }
                    }
                    onSuccess(chatHistory)
                }
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
            })

    }
}

const val FIREBASE_MESSAGE_COLLECTION = "Messages"
const val FIREBASE_GROUP_COLLECTION = "GROUPS"