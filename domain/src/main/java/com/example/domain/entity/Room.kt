package com.example.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    var id: String?=null,
    val name: String?=null,
    val categoryId: String?=null,
    val categoryName: String?=null,
    val descriptionName:String?=null
){
    companion object{
        const val COLLECTION_NAME = "Rooms"
    }
}
