package com.example.goshoes_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.goshoes_kotlin.ui.theme.Gray_bg
import com.example.goshoes_kotlin.viewmodel.FilterOption
import com.example.goshoes_kotlin.viewmodel.MainViewModel
import java.util.logging.Filter
import kotlin.math.ceil

@Composable
fun FilterScreen(viewModel: MainViewModel, navController: NavController) {

    val scrollState = rememberScrollState()
    val filterState by viewModel.filterState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray_bg)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color.White)
            .verticalScroll(scrollState)) {

            FilterSection(
                title = "Brand",
                option = FilterOption.BRAND,
                viewModel = viewModel,
                selectedItems = filterState.selectedBrand
            )

            HorizontalDivider(
                color = Gray_bg,
                modifier = Modifier
                    .height(2.dp)
                    .padding(horizontal = 20.dp)
            )

            FilterSection(
                title = "Color",
                option = FilterOption.COLOR,
                viewModel = viewModel,
                selectedItems = filterState.selectedColor
            )

            HorizontalDivider(
                color = Gray_bg,
                modifier = Modifier
                    .height(2.dp)
                    .padding(horizontal = 20.dp)
            )

            FilterSection(
                title = "Style",
                option = FilterOption.STYLE,
                viewModel = viewModel,
                selectedItems = filterState.selectedStyle
            )

            HorizontalDivider(
                color = Gray_bg,
                modifier = Modifier
                    .height(2.dp)
                    .padding(horizontal = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigateUp()

                    }) {
                    Text(
                        text = "Apply Filter",
                        color = Color.White
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray_bg
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.resetFilters()
                    }) {
                    Text(
                        text = "Reset Filter",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun FilterSection(
    title: String,
    option: FilterOption,
    viewModel: MainViewModel,
    selectedItems: Set<String>
) {
    Text(
        text = title,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(10.dp)
    )

    val filterItems by viewModel.getDistinctFilter(option).collectAsState(emptyList())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .height((ceil(((filterItems.size).toFloat() / 2)) * 36).dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(filterItems) { item ->

            val isChecked = item in selectedItems

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(30.dp)
            ){
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked ->
                        viewModel.toggleFilter(option, item, isChecked)
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White,
                        checkedColor = Color.Black,
                    )
                )

                Text(
                    text = item,
                    color = Color.Black,
                )
            }
        }
    }
}