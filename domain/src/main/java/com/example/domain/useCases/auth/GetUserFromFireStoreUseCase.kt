package com.example.domain.useCases.auth

import com.example.domain.entity.AppUser
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserFromFireStoreUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        uid: String,
        onSuccess: (appUser:AppUser) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        repository.getUser(uid, onSuccess, onFailure)
    }
}