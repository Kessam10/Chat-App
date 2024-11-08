package com.example.domain.useCases.chat

import com.example.domain.entity.Message
import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class AddMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        message: Message,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        repository.addMessage(message, onSuccess, onFailure)
    }
}