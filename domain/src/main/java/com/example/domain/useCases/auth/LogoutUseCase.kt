package com.example.domain.useCases.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        onFailure: (throwable: Throwable) -> Unit
    ) {
        repository.logout(onFailure)
    }

}