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
            if (messageState.value.trim().isNotEmpty()) {
                val message = Message(
                    senderId = DataUtils.appUser?.uid,
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
            listenForMessageUseCase(
                room?.id?:"",
                onMessageFetched = {list->
                    messagesListState.clear()
                    messagesListState.addAll(list)

            },
                onFailure = {
                    showErrorMessage("${it.message}")
                })
        }
    }
}