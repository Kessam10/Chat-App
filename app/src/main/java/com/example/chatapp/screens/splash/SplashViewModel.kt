package com.example.chatapp.screens.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.DataUtils
import com.example.domain.useCases.auth.GetUserFromFireStoreUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserFromFireStoreUseCase: GetUserFromFireStoreUseCase

) : ViewModel() {
    val navigation = mutableStateOf<SplashNavigation>(SplashNavigation.Idle)

    fun navigate() {
        viewModelScope.launch {
            delay(1500)
            val auth = Firebase.auth
            auth.currentUser?.uid?.let {
                getUserFromFireStoreUseCase(
                    it,
                    onSuccess = {user->
                        DataUtils.appUser = user
                        navigation.value = SplashNavigation.Home
                    },
                    onFailure = { navigation.value = SplashNavigation.Login})
            } ?: run { navigation.value = SplashNavigation.Login
            }
        }
    }
}