package com.example.chatapp.screens.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.domain.entity.Message
import com.example.domain.entity.Room
import com.example.domain.useCases.chat.AddMessageUseCase
import com.example.domain.useCases.chat.ListenForMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addMessageUseCase: AddMessageUseCase,
    private val listenForMessageUseCase: ListenForMessageUseCase
) : BaseViewModel() {

    val messageState = mutableStateOf("")
    val messagesListState = mutableStateListOf<Message>()
    var room: Room? = null



    fun sendMessage(roomId: String) {
        viewModelScope.launch {
            val currentUserId = DataUtils.appUser?.uid?:return@launch
            if (messageState.value.trim().isNotEmpty()) {
                val message = Message(
                    senderId = currentUserId,
                    senderName = DataUtils.appUser?.fullName,
                    roomId = roomId,
                    content = messageState.value,
                    dateTime = Date().time
                )
                addMessageUseCase(
                    message,
                    onSuccess = { messageState.value = ""
                        Log.e("TAG", "sendMessage: $roomId", )},
                    onFailure = {
                        showErrorMessage("${it.message}")
                    })
            }
        }
    }

fun listenForMessages() {
    viewModelScope.launch {
        messagesListState.clear() // ✅ Clear old messages before fetching
        listenForMessageUseCase(
            room?.id ?: "",
            onMessageFetched = { list ->
                val sortedMsg = list.sortedBy { it.dateTime }
                messagesListState.addAll(sortedMsg) // ✅ Add fresh messages
            },
            onFailure = {
                showErrorMessage(it.message ?: "Failed to load messages")
            }
        )
    }
}


}