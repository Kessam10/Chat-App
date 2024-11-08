package com.example.domain.repository

import com.example.domain.entity.Message
import com.example.domain.entity.Room

interface ChatRepository {
    suspend fun createRoom(room: Room, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit)
    suspend fun listenForRoomChanges(
        onRoomsFetched: (List<Room>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
    suspend fun addMessage(message: Message,onSuccess: () -> Unit,onFailure: (Throwable) -> Unit)
    suspend fun listenForMessages(roomId:String,onMessagesFetched:(List<Message>)->Unit,onFailure: (Throwable) -> Unit)
}

interface ChatOnlineDataSource {
    suspend fun createRoom(room: Room, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit)
    suspend fun listenForRoomChanges(
        onRoomsFetched: (List<Room>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
    suspend fun addMessage(message: Message,onSuccess: () -> Unit,onFailure: (Throwable) -> Unit)
    suspend fun listenForMessages(roomId:String,onMessagesFetched:(List<Message>)->Unit,onFailure: (Throwable) -> Unit)
}
