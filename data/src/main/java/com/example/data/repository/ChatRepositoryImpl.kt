package com.example.data.repository

import com.example.domain.entity.Message
import com.example.domain.entity.Room
import com.example.domain.repository.ChatOnlineDataSource
import com.example.domain.repository.ChatRepository

class ChatRepositoryImpl(private val onlineDataSource: ChatOnlineDataSource):ChatRepository {
    override suspend fun createRoom(
        room: Room,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        onlineDataSource.createRoom(room, onSuccess, onFailure)
    }

    override suspend fun listenForRoomChanges(
        onRoomsFetched: (List<Room>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        onlineDataSource.listenForRoomChanges(onRoomsFetched, onFailure)
    }

    override suspend fun addMessage(
        message: Message,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        onlineDataSource.addMessage(message,onSuccess, onFailure)
    }

    override suspend fun listenForMessages(
        roomId:String,
        onMessagesFetched: (List<Message>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        onlineDataSource.listenForMessages(roomId,onMessagesFetched,onFailure)
    }
}