package com.example.hallo.Services

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Service for all Auth Functions from Firebase
class AuthService {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    //Add Sign In / Up & Out Functionality
    suspend fun registerNewUser(
        email: String,
        password: String,
        createdUser:(String) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isComplete) {
                    Log.d("AAAA Registered User Success", it.result.user?.uid.toString())
                    it.result.user?.uid?.let { it1 -> createdUser.invoke(it1) }
                } else {
                    Log.d("AAAA Error", it.exception?.localizedMessage.toString())
                    createdUser.invoke("")
                }
            }.await()
    }

    //Add Sign In / Up & Out Functionality
    suspend fun loginUser(
        email: String,
        password: String,
        isCompleted:(Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    Log.d("AAAA Login User Success", "Success")
                    isCompleted.invoke(true)
                } else {
                    Log.d("AAAA Login Error", it.exception?.localizedMessage.toString())
                    isCompleted.invoke(false)
                }
            }.await()
    }

    // Sign Out The User
    fun signOutUser() {
        Firebase.auth.signOut()
    }
}