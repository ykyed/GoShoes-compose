package com.example.goshoes_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.example.goshoes_kotlin.ui.components.CustomTopBar
import com.example.goshoes_kotlin.ui.components.FAB
import com.example.goshoes_kotlin.ui.navigation.Navigation
import com.example.goshoes_kotlin.ui.theme.GoShoes_kotlinTheme
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {

            GoShoes_kotlinTheme {

                val navController = rememberNavController()
                val viewModel = MainViewModel(applicationContext)

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CustomTopBar(
                            modifier = Modifier,
                            navController = navController,
                            viewModel = viewModel,
                        )
                    },
                    floatingActionButton = {
                        val isFABVisible = viewModel.uiState.collectAsState().value.isFABVisible

                        if (isFABVisible) {
                            FAB(
                                painter = painterResource(R.drawable.filter),
                                description = "Filter",
                                onFABClick = {
                                   navController.navigate("filter") {
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {

                        Navigation(applicationContext, viewModel, navController)
                    }
                }
            }
        }
    }
}




