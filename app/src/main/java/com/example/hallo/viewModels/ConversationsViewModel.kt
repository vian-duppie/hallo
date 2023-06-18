package com.example.hallo.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hallo.Services.FirestoreService
import com.example.hallo.models.Conversation
import kotlinx.coroutines.launch

class ConversationsViewModel(
    private val firestoreService: FirestoreService = FirestoreService()
): ViewModel() {

    private var _convoLists = mutableStateListOf<Conversation>()
    val convoList: List<Conversation> = _convoLists

    init {
        getConversations()
    }

    fun getConversations() = viewModelScope.launch {
        firestoreService.getAllConversations() { data ->
            if(data != null) {
                _convoLists.clear()
                for(document in data) {
                    _convoLists.add(document)
                }
            }
        }
    }
}

