package com.example.goshoes_kotlin.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CustomDialog(title: String,
                 text: String,
                 onDismiss: () -> Unit,
                 onCilck:(Boolean) -> Unit) {

    AlertDialog(
        title = { Text( text = title,
            color = Color.Black) },
        text = { Text(text = text,
            color = Color.Black)},
        onDismissRequest = {onDismiss()},
        containerColor = Color.White,
        confirmButton = {
            TextButton(
                onClick = {
                    onCilck(true)
                }
            ) {
                Text(text = "Yes",
                    color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCilck(false)
                }
            ) {
                Text(text = "No",
                    color = Color.Black)
            }
        },

    )
}