package com.example.goshoes_kotlin.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.goshoes_kotlin.model.ShoeEntity
import com.example.goshoes_kotlin.ui.components.RatingBar
import com.example.goshoes_kotlin.ui.theme.Gray_bg
import com.example.goshoes_kotlin.viewmodel.MainViewModel

@Composable
fun ShoeListScreen(mainViewModel: MainViewModel, navController: NavController) {

    Log.d("ShoeListScreen", "start")

    val filteredShoes by mainViewModel.getFilteredShoes().collectAsState(emptyList())
    val allShoes by mainViewModel.shoeList.collectAsState(emptyList())

    val shoeList = if (filteredShoes.isEmpty()) allShoes else filteredShoes

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Gray_bg)
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(shoeList) { shoe ->

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp),
                shape = RoundedCornerShape(3.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                onClick = {
                    navController.navigate("shoeDetail/${shoe.productCode}")
                },
            ) {
                Column {
                    AsyncImage(
                        model = shoe.thumbnail,
                        contentDescription = "Thumbnail",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = shoe.title,
                        fontSize = 13.sp,
                        color = Color.Black,
                        lineHeight = 15.sp,
                        maxLines = 2,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                    //Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "$${shoe.price.toString()}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (shoe.reviewCount > 0) {
                            Log.d("ShoeList", "rating: ${shoe.totalRating}, count: ${shoe.reviewCount}")
                            RatingBar(
                                maxStars = 5,
                                size = 17.0,
                                rating = shoe.totalRating / shoe.reviewCount,
                                onRatingChange = {},
                            )

                            Text(
                                text = "(${shoe.reviewCount})",
                                fontSize = 13.sp,
                                color = Color.Black,
                            )
                        }
                    }
                }
            }
        }
    }
}