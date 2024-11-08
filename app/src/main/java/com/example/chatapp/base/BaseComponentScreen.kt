package com.example.chatapp.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapp.utils.ErrorDialog
import com.example.chatapp.utils.LoadingDialog

@Composable
inline fun<reified VM:BaseViewModel> BaseComposableScreen(content:@Composable (viewModel:VM)->Unit) {
    val viewModel:VM = hiltViewModel()

    content(viewModel)
    LoadingDialog(showLoadingState = viewModel.loadingState)
    ErrorDialog(errorMessage = viewModel.dialogState)
}