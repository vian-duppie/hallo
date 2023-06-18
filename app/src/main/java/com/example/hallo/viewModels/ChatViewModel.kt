package com.example.hallo.viewModels

import com.example.hallo.models.Message
import android.util.Log
import androidx.compose.animation.core.snap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hallo.Services.AuthService
import com.example.hallo.Services.FirestoreService
import com.example.hallo.models.User
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ChatViewModel(
    private val firestoreService: FirestoreService = FirestoreService()
): ViewModel() {
    private val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList


    var messageListener: ListenerRegistration? = null

    private var currentUser: User? = null
    var currentUserId = ""

    init {
        getCurrentProfile()
    }

    fun getRealtimeMessages(chatId: String) {
        Log.d("AAA START listening", chatId)

        val collectionRef = Firebase
            .firestore
            .collection("conversations")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)

        messageListener = collectionRef.addSnapshotListener{snapshot, e ->
            Log.d("AAA listening", "Yes")
            if(e != null) {
                Log.d("AAA listener went wrong", e.localizedMessage)
                return@addSnapshotListener
            }

            if(snapshot != null) {
                Log.d("AAA Received realtime", snapshot.toString())
                _messageList.clear()
                for(document in snapshot) {
                    _messageList.add(
                        document.toObject(Message::class.java)
                    )
                }
                Log.d("Got messages", snapshot.toString())
            }
        }
    }

    private fun getCurrentProfile() = viewModelScope.launch {
        currentUserId = AuthService().getUserId()

        if(currentUserId.isNotBlank()) {
            firestoreService.getUserProfile(currentUserId) {
                currentUser = it
                Log.d("AA received user info: ", it.toString())
            }
        }
    }

    fun sendNewMessage(body: String, chatId: String) = viewModelScope.launch {
        if(body.isNotBlank() && chatId.isNotBlank()) {

            var sendMessage = Message(
                message = body,
                from = currentUser?.username.toString(),
                fromUserId = currentUserId,
                fromUserProfilePic = currentUser?.profileImage.toString()
            )

            firestoreService.addNewMessage(
                newMessage = sendMessage,
                chatId = chatId
            ) {
                Log.d("AAAA added message", it.toString())
            }
        }
    }



    override fun onCleared() {
        messageListener?.remove()
        messageListener = null
    }
}