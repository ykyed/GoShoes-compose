package com.example.goshoes_kotlin.ui.components

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    maxStars: Int = 5,
    size: Double,
    rating: Double,
    onRatingChange: (Double) -> Unit
) {
    //val density = LocalDensity.current.density
    val starSize = (size).dp//(10f * density).dp
    val starSpacing = 0.dp// (0.5f * density).dp

    Row(
      modifier = Modifier.selectableGroup()
          .padding(horizontal = 5.dp),

        //verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xffffc700) else Color(0xff999999)

            Icon(
                imageVector = icon,
                contentDescription = "Rating",
                tint = iconTintColor,
                modifier = Modifier.selectable(
                    selected = isSelected,
                    onClick = {
                        onRatingChange(i.toDouble())
                    }
                ).width(starSize).height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }

    }

}