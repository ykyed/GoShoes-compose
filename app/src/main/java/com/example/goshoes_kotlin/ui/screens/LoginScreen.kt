package com.example.goshoes_kotlin.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goshoes_kotlin.ui.components.InputTextField
import com.example.goshoes_kotlin.ui.theme.Gray_bg
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(context: Context, viewModel: MainViewModel,
                navController: NavController) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Gray_bg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign in",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Sign into your account below.",
                fontSize = 13.sp,
                color = Gray_bg,
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 13.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.width(15.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        navController.navigate("signin")
                    }
                ) {
                    Text(
                        text = "Sign up",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            var email by remember {
                mutableStateOf("")
            }

            InputTextField(
                text = email,
                hint = "Email address",
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email),
                visualTransformation = VisualTransformation.None,
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                onValueChange = {email = it}
            )

            Spacer(modifier = Modifier.height(9.dp))

            var password by remember {
                mutableStateOf("")
            }

            InputTextField(
                text = password,
                hint = "Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                onValueChange = {password = it}
            )

            Spacer(modifier = Modifier.height(30.dp))


            val scope = rememberCoroutineScope()

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                onClick = {

                    scope.launch {
                        viewModel.getUserInfo(email).collect { user ->
                            if (user != null && user.password.equals(password)) {

                                viewModel.saveUsername(user.firstName)
                                Toast.makeText(context, "Successfully Log-in", Toast.LENGTH_SHORT).show()
                                navController.navigateUp()
                            }
                        }
                    }
                }
            ) {
                Text(
                    text = "Sign in",
                    color = Color.White,

                )
            }


        }
    }

}