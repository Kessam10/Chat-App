package com.example.chatapp.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
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
import com.example.chatapp.ChatDestinations
import com.example.chatapp.R
import com.example.chatapp.base.BaseComposableScreen
import com.example.chatapp.screens.entity.Category
import com.example.chatapp.screens.entity.Category.Companion.SPORT
import com.example.chatapp.ui.theme.bluePrimary
import com.example.chatapp.utils.ChatToolbar
import com.example.domain.entity.Room

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    BaseComposableScreen<HomeViewModel> { viewModel ->
        LaunchedEffect(key1 = Unit) {
            viewModel.getRooms()
        }
        Scaffold(floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.navigateToAddRoomScreen()
                },
                contentColor = Color.White,
                containerColor = bluePrimary,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Icon add"
                )
            }
        }, topBar = { ChatToolbar(title = stringResource(id = R.string.home)) }) { innerPadding ->
            innerPadding
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(id = R.drawable.bg),
                        contentScale = ContentScale.Crop
                    )
            ) {
                RoomsLazyVerticalGrid(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                ){room->
                    navController.navigate(room)

                }
            }
        }
        when (viewModel.navigation.value) {
            HomeNavigation.AddRoom -> {
                navController.navigate(ChatDestinations.AddRoom)
                viewModel.navigation.value = HomeNavigation.Idle
            }

            HomeNavigation.Idle -> {}
        }
    }
}

@Composable
fun RoomsLazyVerticalGrid(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onRoomClickListener: (room: Room) -> Unit,
    ) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomListState) { room ->
            Card(
                modifier = modifier
                    .padding(8.dp)
                    .clickable {
                        onRoomClickListener(room)
                    },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(
                        id = Category.fromId(room.categoryId ?: SPORT).categoryImage
                            ?: R.drawable.sports
                    ),
                    contentDescription = stringResource(R.string.category_image_content_description),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(100.dp)
                )
                Text(
                    text = room.name ?: "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}