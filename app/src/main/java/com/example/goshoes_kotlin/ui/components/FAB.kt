package com.example.goshoes_kotlin.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun FAB(painter: Painter, description: String, onFABClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFABClick,
        containerColor = Color.White,
        shape = CircleShape
    ) {
        Icon(
            painter = painter,
            contentDescription = description,
            tint = Color.Black,
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
        )
    }
}