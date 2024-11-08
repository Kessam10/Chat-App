package com.example.chatapp.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.bluePrimary
import com.example.chatapp.ui.theme.lightGray

@Composable
fun ChatAuthTextField(label:String,state:MutableState<String>,error:String,modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxWidth()) {

    TextField(value = state.value, onValueChange = { newValue ->
        state.value = newValue
    },
        modifier = modifier.fillMaxWidth(0.85f),
        colors = TextFieldDefaults.colors(
            errorTextColor = Color.Red,
            focusedIndicatorColor = bluePrimary,
            unfocusedIndicatorColor = lightGray,
            focusedLabelColor = bluePrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent

        ),
        isError = error.isNotEmpty(),
        label = {
            Text(text = label)
        }
        )
        if (error.isNotEmpty()){
            Text(text = error, modifier.align(Alignment.Start).padding(horizontal = 24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatAuthTextFieldPreview() {
    ChatAuthTextField("Email",remember {
        mutableStateOf("")
    }, error = "")
}