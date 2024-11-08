package com.example.domain.useCases.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        repository.login(email, password, onSuccess, onFailure)
    }
}