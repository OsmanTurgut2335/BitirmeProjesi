package com.osman.bitirmeprojesi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.HomeScreenViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel,navController: NavController) {
    val allFoodList = homeScreenViewModel.allFoodList.observeAsState(listOf())

    // Trigger the initial data load
    LaunchedEffect(key1 = true) {
        homeScreenViewModel.loadAllFood()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Welcome") }) }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Set 2 items per row
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(count = allFoodList.value.count()) { index ->
                val food = allFoodList.value[index]
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(colorResource(id = R.color.logintfColor))
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {

                        // Construct the full image URL
                        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"

                        // Load the image using Glide
                        GlideImage(
                            imageModel = { imageUrl },
                            modifier = Modifier
                                .height(100.dp) // Adjust size as needed
                                .fillMaxWidth().clickable {
                                    // Serialize the Food object to JSON
                                    val foodJson = Gson().toJson(food)
                                    // Navigate to the details screen with the JSON parameter
                                    navController.navigate("detailsScreen/$foodJson")
                                },
                            loading = {
                                // Optional: Show a loading indicator while the image is loading
                                CircularProgressIndicator()
                            },
                            failure = {
                                // Optional: Show an error indicator or placeholder if image loading fails
                                Text(text = "Image failed to load")
                            }
                        )

                        Text(text = food.yemek_adi)
                    }
                }
            }
        }
    }
}
