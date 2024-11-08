package com.example.data.dataSource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.domain.entity.Message
import com.example.domain.entity.Room
import com.example.domain.repository.ChatOnlineDataSource
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class ChatOnlineDataSourceImpl(private val firestore: FirebaseFirestore) : ChatOnlineDataSource {
    override suspend fun createRoom(
        room: Room,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            val documentRef = firestore.collection(Room.COLLECTION_NAME).document()
            room.id = documentRef.id
            val result = documentRef.set(room)
                .await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun listenForRoomChanges(
        onRoomsFetched: (List<Room>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            val collectionRef = firestore.collection(Room.COLLECTION_NAME)
            collectionRef.addSnapshotListener { value, error ->
                if (error != null) {
                    onFailure(error)
                    return@addSnapshotListener
                }
                onRoomsFetched(
                    value?.toObjects(Room::class.java) ?: listOf()
                )
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun addMessage(
        message: Message,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            val documentRef =
                firestore.collection(Room.COLLECTION_NAME).document(message.roomId ?: "")
                    .collection(Message.COLLECTION_NAME)
                    .document()
            message.id = documentRef.id
            documentRef.set(message).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun listenForMessages(
        roomId: String,
        onMessagesFetched: (List<Message>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            firestore.collection(Room.COLLECTION_NAME).document(roomId)
                .collection(Message.COLLECTION_NAME)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        onFailure(error)
                        return@addSnapshotListener
                    }
                    val list = mutableListOf<Message>()
                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                list.add(dc.document.toObject(Message::class.java))
                            }
                            DocumentChange.Type.MODIFIED ->{}
                            DocumentChange.Type.REMOVED -> {}
                        }
                    }
                    onMessagesFetched(list)
                }
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}