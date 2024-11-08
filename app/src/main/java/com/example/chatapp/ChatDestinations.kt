package com.example.chatapp

import kotlinx.serialization.Serializable

@Serializable
sealed interface ChatDestinations {

    @Serializable
    data object Splash : ChatDestinations

    @Serializable
    data object Login : ChatDestinations

    @Serializable
    data object Register : ChatDestinations

    @Serializable
    data object Home : ChatDestinations

    @Serializable
    data object AddRoom : ChatDestinations


}