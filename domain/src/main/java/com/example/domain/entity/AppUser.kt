package com.example.domain.entity

data class AppUser(
    val fullName: String? = null,
    val email: String? = null,
    val uid: String? = null
){
    companion object{
        const val COLLECTION_NAME = "Users"
    }
}

