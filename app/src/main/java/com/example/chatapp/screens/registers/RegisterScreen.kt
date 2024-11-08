package com.example.chatapp.screens.registers

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
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BaseComposableScreen<RegisterViewModel> {viewModel->


        Scaffold(
            topBar = {
                ChatToolbar(title = stringResource(R.string.crate_acc), hasBackButton = true){
                    viewModel.navigateUp()
                }
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
                    label = stringResource(R.string.full_name),
                    state = viewModel.fullNameState,
                    error = viewModel.fullNameErrorState.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
                ChatAuthButton(title = stringResource(id = R.string.register),
                    onButtonClicked = {
                        viewModel.register()
                    }
                )
            }
        }
        when(viewModel.navigation.value){
            RegisterNavigation.Home -> {
                navController.clearBackStack(ChatDestinations.Login)
                navController.navigate(ChatDestinations.Home){
                    popUpTo(ChatDestinations.Login){
                        inclusive = true
                    }
                }
            }
            RegisterNavigation.Idle -> {

            }
            RegisterNavigation.NavigateUp -> {
                navController.navigateUp()
            }
        }
    }
}
@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}