package com.example.hallo.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hallo.Services.AuthService
import com.example.hallo.Services.FirestoreService
import com.example.hallo.models.User
import kotlinx.coroutines.launch

class UserViewModel (
    private val firestoreService: FirestoreService = FirestoreService(),
    authService: AuthService = AuthService()
): ViewModel() {
    private val PROFILEDATA = mutableStateOf<User?>(null)
    val profile: User? get() = PROFILEDATA.value
    private val userId = authService.getUserId()
    private var isInitialised = false

    init {
        if(!isInitialised) {
            getUserInformation()
            isInitialised = true
            Log.d("AAA WE GOT THE PROFILE INFO", "OH YEAH")
        }
    }

    private fun getUserInformation() = viewModelScope.launch {
        firestoreService.getUserData(userId){data ->
            data?.let{
                PROFILEDATA.value = it
            }
        }
    }
}