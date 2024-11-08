package com.example.domain.useCases.auth

import com.example.domain.entity.AppUser
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AddUserToFireStoreUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(appUser: AppUser,onSuccess:()->Unit,onFailure:(throwable:Throwable)->Unit){
        repository.addUserToFireStore(appUser,onSuccess,onFailure)
    }
}