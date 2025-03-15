package com.example.chatapp.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chatapp.base.BaseViewModel
import com.example.domain.entity.Room
import com.example.domain.useCases.auth.LogoutUseCase
import com.example.domain.useCases.chat.ListenForRoomChanges
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listenForRoomChanges: ListenForRoomChanges,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {
    val navigation = mutableStateOf<HomeNavigation>(HomeNavigation.Idle)
    val roomListState = mutableStateListOf<Room>()

    fun navigateToAddRoomScreen() {
        navigation.value = HomeNavigation.AddRoom
    }

    fun getRooms() {
        viewModelScope.launch {
            showLoading()
            listenForRoomChanges(
                onRoomsFetched = { rooms ->
                    hideLoading()
                    if (roomListState.isNotEmpty())
                        roomListState.clear()
                    roomListState.addAll(rooms)
                },
                onFailure = {
                    hideLoading()
                    showErrorMessage("${it.message}")
                })
        }
    }


    fun logOut() {
        showLoading()
        viewModelScope.launch {
            try {
                logoutUseCase { throwable ->
                    hideLoading()
                    showErrorMessage(throwable.message ?: "Logout failed")
                }
                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                showErrorMessage(e.message ?: "An error occurred")
            }
        }
    }

}