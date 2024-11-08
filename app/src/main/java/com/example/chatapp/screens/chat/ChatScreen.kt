package com.example.chatapp.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.base.BaseComposableScreen
import com.example.chatapp.ui.theme.bluePrimary
import com.example.chatapp.utils.ChatInputTextField
import com.example.chatapp.utils.ChatToolbar
import com.example.chatapp.utils.MessagesLazyColumn
import com.example.domain.entity.Room

@Composable
fun ChatScreen(room: Room, navController: NavController, modifier: Modifier = Modifier) {
    BaseComposableScreen<ChatViewModel> { viewModel ->


        Scaffold(topBar = {
            ChatToolbar(
                title = "${room.name}",
                hasBackButton = true,
                onBackButtonClicked = {
                    navController.navigateUp()
                })
        }) { innerPadding ->
            innerPadding
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(id = R.drawable.bg),
                        contentScale = ContentScale.Crop
                    ), verticalArrangement = Arrangement.Bottom
            ) {

                Card(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(.9f)
                        .fillMaxHeight(.8f),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    MessagesLazyColumn()

                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    ChatInputTextField(state = viewModel.messageState)
                    Button(
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            viewModel.sendMessage(room.id ?: "")
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = bluePrimary)
                    ) {
                        Text(
                            text = stringResource(R.string.send),
                            modifier = Modifier.padding(10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.send),
                            contentDescription = stringResource(
                                R.string.send_message_button_icon
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatScreen(navController = rememberNavController(), room = Room())
}