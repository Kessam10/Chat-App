package com.example.domain.useCases.auth

import com.example.domain.entity.AppUser
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        user: AppUser,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        repository.register(user, password, onSuccess, onFailure)
    }
}