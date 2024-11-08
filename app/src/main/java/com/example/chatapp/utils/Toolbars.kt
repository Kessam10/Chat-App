package com.example.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatToolbar(
    title: String,
    modifier: Modifier = Modifier,
    hasBackButton: Boolean = false,
    onBackButtonClicked: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (hasBackButton) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onBackButtonClicked?.invoke()
                        },
                    contentDescription = stringResource(R.string.has_back_icon)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatToolbarPreview() {
    ChatToolbar(title = "Login", hasBackButton = true)
}