package com.example.chatapp.screens.splash

sealed interface SplashNavigation {
    data object Idle: SplashNavigation
    data object Login: SplashNavigation
    data object Home: SplashNavigation

}