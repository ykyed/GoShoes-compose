package com.example.goshoes_kotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.goshoes_kotlin.R
import com.example.goshoes_kotlin.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    modifier: Modifier,
    navController: NavController,
    viewModel: MainViewModel
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = Color.White,
            containerColor = Color.Black,
        ),
        title = {
            Image(
                painter = painterResource(id = R.drawable.app_logo_b),
                contentDescription = "Logo",
                modifier = modifier.size(100.dp)
            )
        },
        actions = {
            val isShowActionIcon = viewModel.uiState.collectAsState().value.isActionBtnVisible
            val userName by viewModel.username.collectAsState("")

            if (isShowActionIcon) {
                CustomTopBarIcon(
                    painter = painterResource(R.drawable.cart),
                    description = "Cart",
                    onClick = {
                        navController.navigate("login")
                    }
                )

                CustomTopBarIcon(
                    painter = painterResource(R.drawable.account),
                    description = "Account",
                    userName = userName,
                    onClick = {
                        navController.navigate("login")
                    }
                )
            }
        },
        navigationIcon = {
            val isShowBackIcon = viewModel.uiState.collectAsState().value.isBackBtnVisible
            if (isShowBackIcon) {
                CustomTopBarIcon(
                    painter = painterResource(R.drawable.back),
                    description = "Back",
                    onClick = {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        if (currentRoute == "filter") {
                            viewModel.resetFilters()
                        }

                        navController.navigateUp()
                    }
                )
            }
        }
    )
}

@Composable
fun CustomTopBarIcon(
    painter: Painter,
    description: String,
    userName: String = "",
    onClick: () -> Unit
) {
    Row() {
        IconButton(onClick = onClick) {
            Icon(
                painter = painter,
                contentDescription = description,
                modifier = Modifier.size(30.dp)
            )
        }

        if (userName.isNotEmpty()) {
            Text(
                text = "test",
                color = Color.White
            )
        }
    }
}