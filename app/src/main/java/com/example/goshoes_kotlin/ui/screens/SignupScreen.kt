package com.example.goshoes_kotlin.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.example.goshoes_kotlin.model.UserInfoEntity
import com.example.goshoes_kotlin.ui.components.InputTextField
import com.example.goshoes_kotlin.ui.theme.Gray_bg
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.prefs.Preferences



@Composable
fun SignupScreen(
    context: Context,
    viewModel: MainViewModel,
    navController: NavController
) {
    Box(modifier = Modifier
            .fillMaxSize()
            .background(Gray_bg)
            .padding(5.dp)) {

        Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "My Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            var fName by remember {
                mutableStateOf("")
            }
            InputTextField(
                text = fName,
                hint = "First name",
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text),
                visualTransformation = VisualTransformation.None,
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    },
                onValueChange = {fName = it}
            )

            Spacer(modifier = Modifier.height(10.dp))

            var lName by remember {
                mutableStateOf("")
            }
            InputTextField(
                text = lName,
                hint = "Last name",
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text),
                visualTransformation = VisualTransformation.None,
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    },
                onValueChange = { lName = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    },
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    },
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            val scope = rememberCoroutineScope()

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp),
                onClick = {
                    scope.launch {
                        viewModel.getUserInfo(email).collect { user ->
                            if (user != null) {
                                Toast.makeText(
                                    context,
                                    "Your account is already existed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                viewModel.addUser(
                                    UserInfoEntity(
                                        email = email,
                                        lastName = lName,
                                        firstName = fName,
                                        password = password
                                    )
                                )
                                Toast.makeText(context, "Successfully Sign Up!!", Toast.LENGTH_SHORT).show()
                                navController.navigateUp()
                            }
                        }
                    }
                }
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 15.sp,
                    color = Color.White,
                )
            }
        }
    }
}