package com.example.hallo.Services

import android.content.ContentValues
import android.util.Log
import com.example.hallo.models.Conversation
import com.example.hallo.models.Message
import com.example.hallo.models.User
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

const val USER_REF = "users"
const val CONVERSATION_REF = "conversations"

class FirestoreService {

    val db = Firebase.firestore

    val userRef = db.collection(USER_REF)
    val conversationRef = db.collection(CONVERSATION_REF)

    suspend fun checkIfCodeExists(
        code: String,
//        onSuccess: (Boolean) -> Unit
    ): Boolean {
        var codeExists = false

        try {
            val query = userRef
                .whereEqualTo("code", code)
                .get()
                .await()


            if( !query.isEmpty) {
                codeExists = true
            }
        } catch ( exception: Exception ) {
            Log.e("FIRESTORE", "ERROR GETTING DOCUMENTS")
        }

        return codeExists
    }

    fun createUserInDb(
        uid: String,
        username: String,
        email: String,
        code: String,
        onSuccess: (Boolean) -> Unit,
    ) {
        userRef
            .document(uid)
            .set(
                User(
                    id = uid,
                    username = username,
                    email = email,
                    about = "",
                    profileImage = "",
                    code = code
                )
            )
            .addOnSuccessListener {
                Log.d("AAA User Creation SUCCESS", "YEAYAY")
                onSuccess.invoke(true)
            }
            .addOnFailureListener{
                Log.d("AAA User Creation failed", it.localizedMessage)
                onSuccess.invoke(false)
            }
    }



    suspend fun addNewMessage(
        newMessage: Message,
        chatId: String,
        onSuccess: (Boolean) -> Unit
    ) {
        conversationRef
            .document(chatId)
            .collection("messages")
            .add(newMessage)
            .addOnSuccessListener {messageDocumentReference ->
                conversationRef
                    .document(chatId)
                    .update(mapOf("last_message" to newMessage.message))
                    .addOnSuccessListener {
                        Log.d("AAA new message added", messageDocumentReference.id)
                        onSuccess.invoke(true)
                    }
                    .addOnFailureListener { exception ->
                        Log.d("AAA Problem updating conversation", exception.localizedMessage)
                        exception.printStackTrace()
                        onSuccess.invoke(false)
                    }
//                Log.d("AAA new message added", it.id)
//                onSuccess.invoke(true)
            }
            .addOnFailureListener{
                Log.d("AAAA Problem adding message", it.localizedMessage)
                it.printStackTrace()
                onSuccess.invoke(false)
            }.await()
    }

    suspend fun getUserProfile(
        uid: String,
        onSuccess: (User?) -> Unit
    ) {
        Log.d("AAA getting info", uid)
        userRef
            .document(uid)
            .get()
            .addOnSuccessListener {
                if(it != null) {
                    Log.d(ContentValues.TAG, "DocumentSnapshot")
                    onSuccess.invoke(it?.toObject(User::class.java))
                } else {
                    Log.d(ContentValues.TAG, "AA No such document")
                    onSuccess.invoke(null)
                }
            }
            .addOnFailureListener{ exception ->
                Log.d(ContentValues.TAG, "AA get failed with", exception)
                onSuccess.invoke(null)
            }.await()
    }

    suspend fun getUserData(uid: String, onSuccess: (User?) -> Unit){
        userRef.document(uid).get().addOnSuccessListener { document ->
            if(document != null){
                onSuccess.invoke(document.toObject(User::class.java))
                Log.i("USER", "Get SUCCESS",)
            }
        }
            .addOnFailureListener { exception ->
                Log.i("USER", "Get failed with", exception)
                onSuccess.invoke(null)
            }.await()
    }

    suspend fun updateProfileInformation(
        uid: String,
        username: String,
        about: String,
        email: String,
        profileImage: String,
        onSuccess: (Boolean) -> Unit
    ){
        userRef.document(uid ?: "")
//            .set(user, SetOptions.merge())
            .update(
                "username", username,
                "about", about,
                "email", email,
                "profileImage", profileImage
            )
            .addOnSuccessListener {
                Log.d("AAA Updated User Success: ", "Updated!!!!")
                onSuccess.invoke(true)
            }
            .addOnFailureListener{
                Log.d("AAA Update User Failed", it.localizedMessage)
                onSuccess.invoke(false)
                it.printStackTrace()
            }.await()
    }

    suspend fun getAllConversations(
        onSuccess: (List<Conversation>?) -> Unit
    ) {
        val conversations = arrayListOf<Conversation>()

        conversationRef
            .get()
            .addOnSuccessListener {
                for(document in it) {
                    conversations.add(
                        Conversation(
                            id = document.id,
                            title = document.data["title"].toString(),
                            image = document.data["image"].toString(),
                            last_message = document.data["last_message"].toString()
                        )
                    )
                }
                Log.d("AAA ConversationData", conversations.toString())

                onSuccess(conversations)
            }
            .addOnFailureListener{
                Log.d("AAA Error getting Conversations", it.localizedMessage)
                onSuccess(null)
            }.await()
    }
}


