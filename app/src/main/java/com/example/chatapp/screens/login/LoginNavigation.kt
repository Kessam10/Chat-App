package com.example.chatapp.screens.login

sealed interface LoginNavigation {
    data object Home:LoginNavigation
    data object Idle:LoginNavigation
    data object Register:LoginNavigation
}