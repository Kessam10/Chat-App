package com.example.chatapp.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chatapp.base.BaseViewModel
import com.example.domain.useCases.auth.GetUserFromFireStoreUseCase
import com.example.domain.useCases.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserFromFireStoreUseCase: GetUserFromFireStoreUseCase
) : BaseViewModel() {
    val emailState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")
    val navigation = mutableStateOf<LoginNavigation>(LoginNavigation.Idle)

    fun validateFields(): Boolean {

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

    fun login() {
        if (validateFields()) {
            showLoading()
            viewModelScope.launch {
                try {
                    val response = loginUseCase(
                        emailState.value,
                        passwordState.value,
                        onSuccess = { uid: String ->
                            hideLoading()
                            launch {
                                getUserFromFireStoreUseCase(
                                    uid,
                                    onSuccess = {
                                    hideLoading()
                                    navigation.value = LoginNavigation.Home
                                }, onFailure = {
                                    hideLoading()
                                    showErrorMessage(it.message ?: "")
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

    fun navigateToRegister() {
        navigation.value = LoginNavigation.Register
    }

}