package com.example.domain.useCases.chat

import com.example.domain.entity.Message
import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class ListenForMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        roomId: String,
        onMessageFetched: (List<Message>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        repository.listenForMessages(roomId,onMessageFetched,onFailure)
    }
}