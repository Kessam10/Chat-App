package com.example.chatapp.screens.registers

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chatapp.base.BaseViewModel
import com.example.domain.entity.AppUser
import com.example.domain.useCases.auth.AddUserToFireStoreUseCase
import com.example.domain.useCases.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val addUserToFireStoreUseCase: AddUserToFireStoreUseCase
) : BaseViewModel() {
    val emailState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")
    val fullNameState = mutableStateOf("")
    val fullNameErrorState = mutableStateOf("")
    val navigation = mutableStateOf<RegisterNavigation>(RegisterNavigation.Idle)

    fun validateFields(): Boolean {
        if (fullNameState.value.isEmpty() || fullNameState.value.isBlank()) {
            fullNameErrorState.value = "Please Enter Your Name"
            return false
        } else
            fullNameErrorState.value = ""

        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailErrorState.value = "Please Enter your Name"
            return false
        } else
            emailErrorState.value = ""

        if (passwordState.value.length < 6) {
            passwordErrorState.value = "Password Can't be less than 6 digits or characters"
            return false
        } else
            passwordErrorState.value = ""
        return true
    }

    fun register() {
        if (validateFields()) {
            showLoading()
            viewModelScope.launch {
                try {
                    val user = AppUser(fullName = fullNameState.value, email = emailState.value)
                    val response = registerUseCase(
                        user,
                        password = passwordState.value,
                        onSuccess = { uid: String ->
                            val newUser = user.let {
                                AppUser(it.fullName, it.email, uid)
                            }
                            launch {
                                addUserToFireStoreUseCase(
                                    newUser,
                                    onSuccess = {
                                        hideLoading()
                                        navigation.value = RegisterNavigation.Home
                                    },
                                    onFailure = {
                                        hideLoading()
                                        showErrorMessage(it.message?:"")
                                    })
                            }
                        },
                        onFailure = {
                            hideLoading()
                            showErrorMessage(it.message ?: "")
                        })
                } catch (e: Exception) {
                    hideLoading()
                    showErrorMessage(e.message ?: "")
                }
            }
        }
    }
    fun navigateUp(){
        navigation.value = RegisterNavigation.NavigateUp
    }
}