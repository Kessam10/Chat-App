package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chatapp.screens.addRoom.AddRoomScreen
import com.example.chatapp.screens.chat.ChatScreen
import com.example.chatapp.screens.home.HomeScreen
import com.example.chatapp.screens.login.LoginScreen
import com.example.chatapp.screens.registers.RegisterScreen
import com.example.chatapp.screens.splash.SplashScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.domain.entity.Room
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   innerPadding
                    ChatNavigationNavHost()
                }
            }
        }
    }
}

@Composable
fun ChatNavigationNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ChatDestinations.Splash){
        composable<ChatDestinations.Splash> {
            SplashScreen(navController = navController)
        }
        composable<ChatDestinations.Login> {
            LoginScreen(navController = navController)
        }
        composable<ChatDestinations.Register> {
            RegisterScreen(navController = navController)

        }
        composable<ChatDestinations.Home> {
            HomeScreen(navController = navController)
        }
        composable<ChatDestinations.AddRoom> {
            AddRoomScreen(navController = navController)
        }
        composable<Room> {backStackEntry->
            val room:Room = backStackEntry.toRoute()
            ChatScreen(navController = navController,room = room)

        }
    }
}
