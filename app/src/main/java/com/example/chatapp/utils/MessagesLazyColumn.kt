package com.example.chatapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseComposableScreen
import com.example.chatapp.screens.chat.ChatViewModel
import com.example.chatapp.ui.theme.bluePrimary
import com.example.chatapp.ui.theme.gray2
import com.example.domain.entity.Message

@Composable
fun MessagesLazyColumn(modifier: Modifier = Modifier) {

    BaseComposableScreen<ChatViewModel> { viewModel ->
        LaunchedEffect(key1 = Unit) {
            viewModel.listenForMessages()
        }
        LazyColumn(modifier) {
            items(viewModel.messagesListState.size) { position ->
                if (DataUtils.appUser?.uid == viewModel.messagesListState[position].senderId) {
                    //sent
                    SentMessageCard(message = viewModel.messagesListState[position])

                } else {
                    //received
                    ReceivedMessageCard(message = viewModel.messagesListState[position])
                }
            }
        }
    }
}

@Composable
fun SentMessageCard(message: Message, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = message.formatDataTime(), modifier = Modifier.align(Alignment.Bottom))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = message.content ?: "", modifier = Modifier
                .background(
                    bluePrimary,
                    RoundedCornerShape(bottomStart = 24.dp, topEnd = 24.dp, topStart = 24.dp)
                )
                .padding(8.dp)
                .padding(end = 8.dp), color = Color.White
        )

    }
}

@Preview
@Composable
private fun SentMessageCardPreview() {
    SentMessageCard(Message(content = "Hello", dateTime = 2333232))
}


@Composable
fun ReceivedMessageCard(message: Message, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp))
    {
        Text(text = message.senderName ?: "", modifier = Modifier.padding(start = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = message.content ?: "", modifier = Modifier
                    .padding(4.dp)
                    .background(
                        gray2,
                        RoundedCornerShape(bottomEnd = 24.dp, topEnd = 24.dp, topStart = 24.dp)
                    )
                    .padding(8.dp)
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = message.formatDataTime(), modifier = Modifier.align(Alignment.Bottom))


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReceivedMessageCardPreview() {
    ReceivedMessageCard(Message(content = "Hello", senderName = "kareem", dateTime = 2333232))
}


@Preview(showBackground = true)
@Composable
private fun MessagesLazyColumnPreview() {
    MessagesLazyColumn()
}