package com.example.goshoes_kotlin.ui.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goshoes_kotlin.ui.screens.CartScreen
import com.example.goshoes_kotlin.ui.screens.FilterScreen
import com.example.goshoes_kotlin.ui.screens.LoginScreen
import com.example.goshoes_kotlin.ui.screens.ShoeDetailScreen
import com.example.goshoes_kotlin.ui.screens.ShoeListScreen
import com.example.goshoes_kotlin.ui.screens.SignupScreen
import com.example.goshoes_kotlin.viewmodel.MainViewModel

@Composable
fun Navigation(context: Context, viewModel: MainViewModel, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "shoeList"
    ) {
        composable("shoeList") {

            Log.d("Navigation", "shoeList")

            viewModel.updateUIState(
                isBackBtnVisible = false,
                isActionBtnVisible = true,
                isFABVisible = true
            )

            ShoeListScreen(viewModel, navController)
        }
        composable("shoeDetail/{productCode}") { content ->

            Log.d("Navigation", "shoeDetail")

            viewModel.updateUIState(
                isBackBtnVisible = true,
                isActionBtnVisible = true,
                isFABVisible = false
            )

            val productCode = content.arguments?.getString("productCode")
            productCode?.let {
                ShoeDetailScreen(viewModel, productCode, navController)
            }
        }
        composable("filter") {

            Log.d("Navigation", "filter")
            viewModel.updateUIState(
                isBackBtnVisible = true,
                isActionBtnVisible = true,
                isFABVisible = false
            )
            FilterScreen(viewModel, navController)
        }

        composable("login") {
            Log.d("Navigation", "login")
            viewModel.updateUIState(
                isBackBtnVisible = true,
                isActionBtnVisible = false,
                isFABVisible = false
            )
            LoginScreen(context, viewModel, navController)
        }

        composable("signin") {
            Log.d("Navigation", "signin")
            viewModel.updateUIState(
                isBackBtnVisible = true,
                isActionBtnVisible = false,
                isFABVisible = false
            )
            SignupScreen(context, viewModel, navController)
        }

        composable("cart") {
            Log.d("Navigation", "cart")
            viewModel.updateUIState(
                isBackBtnVisible = true,
                isActionBtnVisible = false,
                isFABVisible = false
            )

            CartScreen(viewModel)
        }
    }
}