package com.example.chatapp.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ChatDestinations
import com.example.chatapp.R

@Composable
fun SplashScreen(navController: NavController,modifier: Modifier = Modifier,viewModel: SplashViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.navigate()
    }
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.image_of_the_app),
            modifier = Modifier.fillMaxHeight(0.22f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = stringResource(R.string.signature),
            modifier = Modifier.fillMaxHeight(0.2f),
            contentScale = ContentScale.Crop
        )

    }
    when(viewModel.navigation.value){
        SplashNavigation.Home -> {
            navController.navigate(ChatDestinations.Home)
        }
        SplashNavigation.Idle -> {

        }
        SplashNavigation.Login -> {
            navController.clearBackStack(ChatDestinations.Splash)
            navController.navigate(ChatDestinations.Login){
                popUpTo(ChatDestinations.Splash){
                    inclusive = true
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}