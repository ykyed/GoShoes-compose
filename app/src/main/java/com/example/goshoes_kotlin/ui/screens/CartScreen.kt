package com.example.goshoes_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.goshoes_kotlin.R
import com.example.goshoes_kotlin.model.CartEntity
import com.example.goshoes_kotlin.ui.theme.Gray_bg
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun CartScreen(viewModel: MainViewModel) {

    val userEmail by viewModel.useremail.collectAsState("")
    val cartItems by viewModel.getCartItemByUser(userEmail).collectAsState(emptyList())

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Gray)
    ) {

        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White).padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.cart),
                    contentDescription = "",
                    tint = Color.Black,
                )

                Text(
                    text = "Your cart is currently empty",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        else {

            val scrollState = rememberScrollState()

            Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .verticalScroll(scrollState)) {

                repeat(cartItems.size) { iteration ->

//                    var cartItem: CartEntity by remember {
//                        mutableStateOf<CartEntity>()
//                    }

                   val cartItem = cartItems[iteration]

                    val shoeItem by viewModel.getShoe(cartItem.productCode).collectAsState(null)

                    if (shoeItem != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(3.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()
                                    .padding(horizontal = 15.dp)
                            ) {
                                AsyncImage(
                                    model = shoeItem?.thumbnail,
                                    contentDescription = "Thumbnail",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.size(100.dp)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    modifier = Modifier.width(220.dp)
                                ) {
                                    Text(
                                        text = shoeItem!!.title,
                                        color = Color.Black,
                                        maxLines = 1,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = "size: ${cartItem.size}",
                                        color = Color.Black,
                                        maxLines = 1,
                                        fontSize = 15.sp,
                                    )

                                    Text(
                                        text = "$${shoeItem!!.price}",
                                        color = Color.Black,
                                        maxLines = 1,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                val scope = rememberCoroutineScope()

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Plus",
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(15.dp)
                                            .border(1.dp, Color.Gray, RoundedCornerShape(1.dp))
                                            .clickable {
                                            scope.launch {
                                                viewModel.updateItem(cartItem.copy(quantity = cartItem.quantity + 1))
                                            }
                                        }
                                    )


                                    Text(text = "${cartItem.quantity}", color = Color.Black)

                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Remove",
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(15.dp)
                                            .border(1.dp, Color.Gray, RoundedCornerShape(1.dp))
                                            .clickable {
                                            scope.launch {
                                                if (cartItem.quantity == 1) {
                                                    viewModel.deleteItem(cartItem.id)
                                                }
                                                else {
                                                    viewModel.updateItem(cartItem.copy(quantity = cartItem.quantity - 1))
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider(
                        color = Gray_bg,
                        modifier = Modifier
                            .height(2.dp)
                            .padding(horizontal = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text(
                        text = "Checkout",
                        fontSize = 15.sp,
                        color = Color.White,
                    )

                }
            }

        }



    }
}