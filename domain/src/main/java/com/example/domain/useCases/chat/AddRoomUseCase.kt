package com.example.domain.useCases.chat

import com.example.domain.entity.Room
import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class AddRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(room: Room,onSuccess:()->Unit,onFailure:(Throwable)->Unit){
        repository.createRoom(room, onSuccess, onFailure)
    }
}