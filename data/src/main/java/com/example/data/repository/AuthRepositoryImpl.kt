package com.example.data.repository

import com.example.domain.entity.AppUser
import com.example.domain.repository.AuthOnlineDataSource
import com.example.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val onlineDataSource: AuthOnlineDataSource
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        onlineDataSource.login(email, password, onSuccess, onFailure)
    }

    override suspend fun register(
        user: AppUser,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        onlineDataSource.register(user, password, onSuccess, onFailure)
    }

    override suspend fun addUserToFireStore(
        appUser: AppUser,
        onSuccess: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        onlineDataSource.addUserToFireStore(appUser, onSuccess, onFailure)
    }

    override suspend fun getUser(
        uid: String,
        onSuccess: (appUser: AppUser) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        onlineDataSource.getUser(uid, onSuccess, onFailure)
    }


}