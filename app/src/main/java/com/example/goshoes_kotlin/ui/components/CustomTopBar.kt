package com.example.goshoes_kotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import com.example.goshoes_kotlin.R
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

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

            if (isShowActionIcon) {

                val userName by viewModel.username.collectAsState("")
                val userEmail by viewModel.useremail.collectAsState("")
                val cartItems by viewModel.getCartItemByUser(userEmail).collectAsState(emptyList())

                BadgedBox(
                    badge = {
                        if (cartItems.isNotEmpty()) {
                            Badge(
                                containerColor = Color.White,
                                modifier = Modifier.offset(x = -3.dp, y = -5.dp)
                            ) {

                                Text(
                                    text = cartItems.size.toString(),
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cart),
                        contentDescription = "Cart",
                        modifier = Modifier.size(30.dp)
                            .clickable {
                                if (userName.isNotEmpty()) {
                                    navController.navigate("cart")
                                }
                                else {
                                    navController.navigate("login")
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                var showDialog by remember {
                    mutableStateOf(false)
                }

                CustomTopBarIcon(
                    painter = painterResource(R.drawable.account),
                    description = "Account",
                    userName = userName,
                    onClick = {
                        if (userName.isNotEmpty()) {
                            showDialog = true
                        }
                        else {
                            navController.navigate("login")
                        }
                    }
                )

                if (showDialog) {
                    CustomDialog(
                        title = "Sign out",
                        text = "Do you want to sign out?",
                        onDismiss = {showDialog = false},
                        onCilck = { isConfirm ->
                            if (isConfirm) {
                                viewModel.clearUsername()
                            }
                            showDialog = false
                        })
                }
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
    Icon(
        painter = painter,
        contentDescription = description,
        modifier = Modifier.size(29.dp).clickable { onClick() },
    )

    if (userName.isNotEmpty()) {
        Column(
            modifier = Modifier.height(29.dp).padding(start = 4.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Hi, ${userName.slice(0..2)}",
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier.height(18.dp),
            )
        }

    }
}