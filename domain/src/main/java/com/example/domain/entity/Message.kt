package com.example.domain.entity

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    var id: String?=null,
    val senderName:String?=null,
    val senderId:String?=null,
    val content:String?=null,
    val dateTime:Long?=null,
    val roomId:String?=null
){
    fun formatDataTime():String{
        val date = Date(dateTime?:0L)
        val simpleDateFormat  = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    companion object{
        const val COLLECTION_NAME = "Messages"
    }
}
