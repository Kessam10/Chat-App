package com.example.chatapp.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ChatDestinations
import com.example.chatapp.R
import com.example.chatapp.base.BaseComposableScreen
import com.example.chatapp.utils.ChatAuthButton
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.ChatToolbar

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BaseComposableScreen<LoginViewModel> {viewModel->

        Scaffold(
            topBar = {
                ChatToolbar(title = stringResource(R.string.login))
            },
        ) { innerPadding ->
            innerPadding
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(id = R.drawable.bg),
                        contentScale = ContentScale.FillBounds
                    ),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.4f))
                Text(
                    text = stringResource(R.string.welcome_back), modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ChatAuthTextField(
                    label = stringResource(R.string.email),
                    state = viewModel.emailState,
                    error = viewModel.emailErrorState.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ChatAuthTextField(
                    label = stringResource(R.string.password),
                    state = viewModel.passwordState,
                    error = viewModel.passwordErrorState.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ChatAuthButton(title = stringResource(id = R.string.login),
                    onButtonClicked = {
                        viewModel.login()
                    })
                Text(
                    text = stringResource(R.string.or_create_account),
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(start = 12.dp)
                        .align(Alignment.Start)
                        .clickable {
                            navController.navigate(ChatDestinations.Register)
                        }
                )
            }
            when(viewModel.navigation.value){
                LoginNavigation.Home -> {
                    navController.clearBackStack(ChatDestinations.Home)
                    navController.navigate(ChatDestinations.Home){
                        popUpTo(ChatDestinations.Login){
                            inclusive = true
                        }
                    }
                    viewModel.navigation.value = LoginNavigation.Idle
                }
                LoginNavigation.Idle -> {

                }
                LoginNavigation.Register -> {
                    navController.navigate(ChatDestinations.Register)
                    viewModel.navigation.value = LoginNavigation.Idle
                }

            }
        }
    }
}
@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}