package com.example.hallo.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hallo.Services.AuthService
import com.example.hallo.Services.CONVERSATION_REF
import com.example.hallo.Services.FirestoreService
import com.example.hallo.Services.USER_REF
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthViewModel (
    private val authService: AuthService = AuthService(),
    private val firestoreService: FirestoreService = FirestoreService()
): ViewModel() {
    val currentUser = authService.currentUser

    val hasUser: Boolean get() = authService.hasUser()

    var authUiState by mutableStateOf(AuthUiState())
        private set

    fun handleInputStateChanges(target: String, value: String) {
        when (target) {
            "loginEmail" -> {
                authUiState = authUiState.copy(loginEmail = value)
            }

            "loginPassword" -> {
                authUiState = authUiState.copy(loginPassword = value)
            }

            "registerUsername" -> {
                authUiState = authUiState.copy(registerUsername = value)
            }

            "registerEmail" -> {
                authUiState = authUiState.copy(registerEmail = value)
            }

            "registerPassword" -> {
                authUiState = authUiState.copy(registerPassword = value)
            }
        }
    }

    // Function to generate a unique 5-character code
    private suspend fun generateUniqueCode(email: String): String = withContext(Dispatchers.IO) {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val emailWithoutDomain = email.substringBefore("@")
        val code = StringBuilder()

        // Generate the code based on the user's email and the alphabet
        for (i in 0 until 5) {
            val index = (emailWithoutDomain.hashCode() + i) % alphabet.length
            code.append(alphabet[index])
        }

        // Check if the code already exists in the database (you need to implement this logic)
        val codeExists = firestoreService.checkIfCodeExists(code.toString())

        // If the code exists, generate a new one recursively until a unique code is found
        return@withContext if (codeExists) {
            generateUniqueCode(email)
        } else {
            code.toString()
        }
    }


    fun createNewUser(context: Context) = viewModelScope.launch {
        authUiState = authUiState.copy(errorMessage = "")

        try {
            if (authUiState.registerUsername.isBlank() || authUiState.registerEmail.isBlank() || authUiState.registerPassword.isBlank()) {
                authUiState = authUiState.copy(errorMessage = "Please fill in all of the fields.")
                Log.d("AAAA ERROR", "PASSWORD TOO SHORT")
            } else if(authUiState.registerPassword.length < 6){
                authUiState = authUiState.copy(errorMessage = "Password must be at least 6 characters long.")
                Log.d("AAAA ERROR", "PASSWORD TOO SHORT")
            } else {
                authUiState = authUiState.copy(isLoading = true)

                var generatedCode = generateUniqueCode(authUiState.registerEmail)

                // Do the Auth
                authService.registerNewUser(
                    authUiState.registerEmail,
                    authUiState.registerPassword
                ) { userId ->
                    if(userId.isNotBlank()) {
                        // TODO: Add user to Firestore
                        FirestoreService()
                            .createUserInDb(
                                uid = userId,
                                username = authUiState.registerUsername,
                                email = authUiState.registerEmail,
                                code = generatedCode
                            ){
                                Log.d("AAAA TESTING", it.toString())
                                if(it) {
                                    Toast.makeText(
                                        context,
                                        "Added User",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    authUiState = authUiState.copy(authSuccess = true)
                                } else {
                                    Log.d("AAAA TESTING", "Creating User Failed")
                                    Log.d("AAAA TESTING", it.toString())

                                    Toast.makeText(
                                        context,
                                        "Adding User Failed",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    authUiState = authUiState.copy(authSuccess = false)
                                }
                            }
                    } else {
                        Log.d("AAAA Error with Register", "Something wrong")

                        Toast.makeText(
                            context,
                            "There was an error with your registration",
                            Toast.LENGTH_SHORT
                        ).show()

                        authUiState = authUiState.copy(authSuccess = false)
                        authUiState = authUiState.copy(errorMessage = "Invalid Email and/or Password")
                    }
                 }
            }
        } catch ( e: Exception ) {
            Log.d("AAAA Error with register", e.localizedMessage)
            e.printStackTrace()
        } finally {
            authUiState = authUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        authUiState = authUiState.copy(errorMessage = "")

        try {
            if (authUiState.loginEmail.isBlank() || authUiState.loginPassword.isBlank()) {
                authUiState = authUiState.copy(errorMessage = "Please fill in all of the fields.")
            } else {
                authUiState = authUiState.copy(isLoading = true)

                // Do the Auth
                authService.loginUser(
                    authUiState.loginEmail,
                    authUiState.loginPassword
                ) { isCompleted ->
                    if(isCompleted) {
                        Log.d("AAAA Register success", "WELL DONE")

                        Toast.makeText(
                            context,
                            "Login Completed",
                            Toast.LENGTH_SHORT
                        ).show()

                        authUiState = authUiState.copy(authSuccess = true)
                    } else {
                        Log.d("AAAA Error with Login", "Something wrong")

                        Toast.makeText(
                            context,
                            "Login failed",
                            Toast.LENGTH_SHORT
                        ).show()

                        authUiState = authUiState.copy(authSuccess = false)
                        authUiState = authUiState.copy(errorMessage = "Invalid Email and/or Password")
                    }
                }
            }
        } catch ( e: Exception ) {
            Log.d("AAAA Error with register", e.localizedMessage)
            e.printStackTrace()
        } finally {
            authUiState = authUiState.copy(isLoading = false)
        }
    }
}

// Frontend State Management
data class AuthUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authSuccess: Boolean = false,
    val loginEmail: String = "",
    val loginPassword: String = "",
    val registerUsername: String = "",
    val registerEmail: String = "",
    val registerPassword: String = ""
)