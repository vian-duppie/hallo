package com.example.hallo.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hallo.Services.AuthService
import com.example.hallo.Services.FirestoreService
import com.example.hallo.Services.StorageService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: AuthService = AuthService(),
    private val firestoreService: FirestoreService = FirestoreService(),
    private val storageService: StorageService = StorageService()
): ViewModel() {
    private val hasUser = authService.hasUser()
    private val currentUserId = authService.getUserId()

    init {
        Log.d("AAA PROFILE VIEW MODEL", "INIT")
        getProfileData()
    }

    var profileUiState by mutableStateOf(ProfileUiState())
        private set

    var oldImage: String = ""

    fun handleAboutStateChange(value: String) {
        profileUiState = profileUiState.copy(about = value)
    }

    fun handeUsernameStateChange(value: String) {
        profileUiState = profileUiState.copy(username = value)
    }

    fun handleProfileStateChange(value: Uri) {
        profileUiState = profileUiState.copy(profileImage = value)
    }

    fun getProfileData() = viewModelScope.launch {
        viewModelScope.launch {
            if( currentUserId.isNotBlank() ) {
                firestoreService.getUserData(currentUserId){
                    profileUiState = profileUiState.copy(
                        about = it?.about ?: "",
                        username = it?.username ?: "",
                        email = it?.email ?: "",
                        profileImage = Uri.parse(it?.profileImage)
                    )
                    oldImage = it?.profileImage ?: ""
                    Log.d("AAA received user info: ", it.toString())
                }
            }
        }
    }

    fun saveProfileData() = viewModelScope.launch {
        Log.d("AAA Profile States", profileUiState.toString())
        if(hasUser){
            var downloadUrl = oldImage
            Log.d("AAA Profile Download", downloadUrl)
            // if a new image was selected, upload the new one
            if(oldImage != profileUiState.profileImage.toString() || oldImage.isBlank()){
                Log.d("AAA new image selected...", "YES!")

                storageService.uploadImageToStorage(
                    imageUri = profileUiState.profileImage,
                    fileName = "$currentUserId-${profileUiState.email}"
                ) {
                    downloadUrl = it
                }

                Log.d("AAA new image URL", downloadUrl)

                firestoreService.updateProfileInformation(
                    uid = currentUserId,
                    username = profileUiState.username,
                    about = profileUiState.about,
                    email = profileUiState.email,
                    profileImage = downloadUrl
                ){
                    Log.d("AAA Updated User?", it.toString())
                }
            } else {
                firestoreService.updateProfileInformation(
                    uid = currentUserId,
                    username = profileUiState.username,
                    about = profileUiState.about,
                    email = profileUiState.email,
                    profileImage = downloadUrl
                ){
                    Log.d("AAA Updated User?", it.toString())
                }
            }
        }
    }
}

data class ProfileUiState(
    val username: String = "",
    val about: String = "",
    val email: String = "",
    val profileImage: Uri = Uri.EMPTY,
)