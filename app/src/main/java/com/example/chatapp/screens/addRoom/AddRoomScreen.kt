package com.example.chatapp.screens.addRoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.ChatToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(navController: NavController, modifier: Modifier = Modifier) {
    BaseComposableScreen<AddRoomViewModel> { viewModel ->

        Scaffold(
            topBar = {
                ChatToolbar(
                    title = stringResource(R.string.chat_app),
                    hasBackButton = true,
                    onBackButtonClicked = {
                        navController.navigateUp()
                    },
                    onLogoutClicked = {},
                    hasLogoutButton = false
                )
            }
        ) { innerPadding ->
            innerPadding
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .paint(painterResource(id = R.drawable.bg), contentScale = ContentScale.Crop)
            ) {
                Card(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(40.dp)
                        .fillMaxWidth(.85f),
                    colors = CardDefaults.cardColors(containerColor = Color.White,)
                ) {
                    Text(
                        text = stringResource(id = R.string.add_new_room),
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.create_room_image),
                        contentDescription = stringResource(
                            R.string.image_of_create_room
                        ), contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    ChatAuthTextField(
                        label = stringResource(R.string.room_name),
                        state = viewModel.roomNameState,
                        error = viewModel.roomNameErrorState.value
                    )

                    ExposedDropdownMenuBox(
                        expanded = viewModel.isDropDownExpanded.value,
                        onExpandedChange = { newExpandedValue ->
                            viewModel.isDropDownExpanded.value = newExpandedValue
                        }, modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        OutlinedTextField(
                            value = viewModel.selectedCategory.value.categoryName ?: "",
                            onValueChange = {},

                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            leadingIcon = {
                                Image(
                                    painter = painterResource(
                                        id = viewModel.selectedCategory.value.categoryImage
                                            ?: R.drawable.sports
                                    ),
                                    contentDescription = stringResource(R.string.selected_room_category_image),
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            //trailingIcon = ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isDropDownExpanded.value)
                        )
                        ExposedDropdownMenu(
                            expanded = viewModel.isDropDownExpanded.value,
                            onDismissRequest = { viewModel.isDropDownExpanded.value = false }) {
                            viewModel.categoriesList.forEach { category ->
                                DropdownMenuItem(text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(
                                                id = category.categoryImage ?: R.drawable.sports
                                            ),
                                            contentDescription = stringResource(
                                                R.string.category_image
                                            ), modifier = Modifier.size(50.dp)
                                        )
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Text(text = category.categoryName ?: "")
                                    }
                                }, onClick = {
                                    viewModel.selectedCategory.value = category
                                    viewModel.isDropDownExpanded.value = false
                                })
                            }
                        }
                    }

                    ChatAuthTextField(
                        label = stringResource(R.string.room_desc),
                        state = viewModel.roomDescState,
                        error = viewModel.roomDescErrorState.value
                    )
                    Button(onClick = {
                        viewModel.addRoomToFirestore()
                    }, colors = ButtonDefaults.buttonColors(containerColor = bluePrimary),modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = stringResource(R.string.create_room))
                    }
                }
            }
        }
        if (viewModel.isDone.value){
            navController.navigateUp()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddRoomScreenPreview() {
    AddRoomScreen(rememberNavController())
}