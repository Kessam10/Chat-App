package com.example.domain.useCases.chat

import com.example.domain.entity.Room
import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class ListenForRoomChanges @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        onRoomsFetched: (List<Room>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        repository.listenForRoomChanges(onRoomsFetched, onFailure)
    }
}