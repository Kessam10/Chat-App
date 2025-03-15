package com.example.chatapp.screens.home

import com.example.chatapp.screens.login.LoginNavigation

interface HomeNavigation {
    data object Idle: HomeNavigation
    data object AddRoom: HomeNavigation
    data object Logout:HomeNavigation
}