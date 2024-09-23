package com.osman.bitirmeprojesi.views

import BottomNavigationBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.FavoritesScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.HomeScreenViewModel
import com.osman.bitirmeprojesi.views.customviews.TopBarText
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesScreenViewModel: FavoritesScreenViewModel,
) {
    // Get the list of favorite foods from HomeScreenViewModel
    val favoriteFoods = favoritesScreenViewModel.getFavoriteFoods()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        topBar = { TopAppBar(
            title = {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Favoriler")
                }
            } ,
            actions = {
                // Trash icon button
                IconButton(onClick = {
                    // Navigate back to HomeScreen when trash icon is clicked
                    navController.navigate("homeScreen") {
                        popUpTo("homeScreen") { inclusive = true }
                    }
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Delete")
                }
            }) }
    ) { paddingValues ->
        if (favoriteFoods.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 items per row
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(favoriteFoods) { food ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.textColor))
                            .clickable {
                                // Serialize the Food object to JSON
                                val foodJson = Gson().toJson(food)
                                // Navigate to the details screen with the JSON parameter
                                navController.navigate("detailsScreen/$foodJson")
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // The main content of the card
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                // Construct the full image URL
                                val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"

                                // Load the image using Glide
                                GlideImage(
                                    imageModel = { imageUrl },
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(100.dp),
                                    loading = {
                                        CircularProgressIndicator()
                                    },
                                    failure = {
                                        Text(text = "Image failed to load")
                                    }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(text = food.yemek_adi, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        } else {
            // Show a message if there are no favorite foods
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Henüz bir favoriniz yok.")
            }
        }
    }
}

