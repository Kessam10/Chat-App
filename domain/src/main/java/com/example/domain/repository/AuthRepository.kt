package com.example.domain.repository

import com.example.domain.entity.AppUser

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun register(
        user: AppUser,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun addUserToFireStore(
        appUser: AppUser,
        onSuccess: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun getUser(
        uid: String,
        onSuccess: (appUser:AppUser) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )
}

interface AuthOnlineDataSource {
    suspend fun login(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun register(
        user: AppUser,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun addUserToFireStore(
        appUser: AppUser,
        onSuccess: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun getUser(
        uid: String,
        onSuccess: (appUser:AppUser) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )
}