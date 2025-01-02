package com.example.goshoes_kotlin.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.goshoes_kotlin.R
import com.example.goshoes_kotlin.ui.components.RatingBar
import com.example.goshoes_kotlin.ui.theme.Gray_bg
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun ShoeDetailScreen(
    viewModel: MainViewModel,
    productCode: String,
    navController: NavHostController
) {

    var shoeImages by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    LaunchedEffect(productCode) {
        launch {
            shoeImages = viewModel.getImages(productCode)
        }
    }

    val shoeEntity by viewModel.getShoe(productCode).collectAsState(null)
    val sizeList by viewModel.getSizeInfo(productCode).collectAsState(emptyList())

    var selectedSize by remember {
        mutableIntStateOf(-1)
    }

    shoeEntity?.let { shoe ->

        val pagerState = rememberPagerState(pageCount = {shoeImages.size})

        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .background(Gray_bg)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(scrollState)
            ) {

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    AsyncImage(
                        model = shoeImages[page],
                        contentDescription = "Images",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().
                        height(400.dp)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(10.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(iteration)
                                    }
                                }
                        )
                   }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = shoe.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "$${shoe.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Select a size:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(135.dp),
                ) {
                    items(15) { iteration ->

                        if (sizeList.isNotEmpty()) {
                            Log.d("ShoeDetail", sizeList[iteration].quantity.toString())
                            val isEnabled = sizeList[iteration].quantity > 0
                            val borderWidth = if (isEnabled) 1 else 0

                            Button(
                                onClick = {
                                    selectedSize = iteration
                                },
                                shape = RoundedCornerShape(3.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedSize != iteration) Color.White else Color.Black,
                                    disabledContainerColor = Gray_bg
                                ),

                                modifier = Modifier
                                    .border(
                                        width = (borderWidth).dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .height(40.dp),
                                enabled = isEnabled,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "US${sizeList[iteration].size.toString()}",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    color = if (selectedSize == iteration || !isEnabled) Color.White else Color.Black,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black,
                        disabledContainerColor = Gray_bg),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    enabled = selectedSize != -1
                ) {
                    Text(
                        text = "Add to Cart",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))

                HorizontalDivider(
                    color = Gray_bg,
                    modifier = Modifier
                        .height(2.dp)
                        .padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Reviews (${shoe.reviewCount})",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingBar(
                            maxStars = 5,
                            size = 15.0,
                            rating = shoe.totalRating,
                            onRatingChange = {}
                        )

                        Text(
                            text = shoe.totalRating.toString(),
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(R.drawable.download),
                            contentDescription = "Down",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(start = 5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}