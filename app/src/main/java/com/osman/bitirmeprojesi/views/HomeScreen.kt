package com.osman.bitirmeprojesi.views

import BottomNavigationBar
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.HomeScreenViewModel
import com.osman.bitirmeprojesi.views.customviews.AnimatedPreloader
import com.osman.bitirmeprojesi.views.customviews.CustomHeaderText
import com.osman.bitirmeprojesi.views.customviews.CustomText
import com.osman.bitirmeprojesi.views.customviews.CustomTopBar
import com.osman.bitirmeprojesi.views.customviews.SortCriteria
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel, navController: NavController) {
    val allFoodList = homeScreenViewModel.allFoodList.observeAsState(listOf())

    // Get the favorites list from the ViewModel
    val favoriteFoods = remember { mutableStateListOf<Food>().apply {
        addAll(homeScreenViewModel.getFavoriteFoods())
    }}
    var expanded by remember { mutableStateOf(false) } // To handle the dropdown state
    var sortExpanded by remember { mutableStateOf(false) } // For the sort dropdown
    var sortCriteria by remember { mutableStateOf<SortCriteria?>(null) }

    // Trigger the initial data load
    LaunchedEffect(key1 = true) {
        homeScreenViewModel.loadAllFood()
    }

    // Sort the list based on selected criteria
    val sortedFoodList = remember(allFoodList.value, sortCriteria) {
        println("Sorting criteria: $sortCriteria")
        when (sortCriteria) {
            SortCriteria.Name -> allFoodList.value.sortedBy { it.yemek_adi }
            SortCriteria.PriceAscending -> allFoodList.value.sortedBy { it.yemek_fiyat.toDoubleOrNull() ?: 0.0 }
            SortCriteria.PriceDescending -> allFoodList.value.sortedByDescending { it.yemek_fiyat.toDoubleOrNull() ?: 0.0 }
            else -> allFoodList.value

        }

    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Hoşgeldiniz",
                navController = navController,
                sortExpanded = sortExpanded,
                onSortExpandedChange = { sortExpanded = it },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                allFoodList = allFoodList.value,
                onSortSelected = { criteria ->
                    println("Sort selected: $criteria") // Debug statement
                    sortCriteria = criteria

                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Set 2 items per row
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(count = sortedFoodList.count()) { index ->
                val food = sortedFoodList[index]

                // Manage the quantity state using remember
                var quantity by remember { mutableStateOf(0) } // Initial quantity set to 0

                // Check if the current food item is a favorite
                val isFavorite = remember { mutableStateOf(favoriteFoods.contains(food)) }

                // Convert price from String to Double for calculation
                val price = food.yemek_fiyat.toIntOrNull() ?: 0
                val totalPrice = quantity * price

                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .shadow(elevation = 2.dp)
                        .background(color = colorResource(id = R.color.redBackground)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // The main content of the card
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
                                    .width(100.dp)
                                    .clickable {
                                        // Serialize the Food object to JSON
                                        val foodJson = Gson().toJson(food)
                                        // Navigate to the details screen with the JSON parameter
                                        navController.navigate("detailsScreen/$foodJson")
                                    },
                                loading = {

                                   // CircularProgressIndicator()

                                    AnimatedPreloader(
                                        modifier = Modifier.size(100.dp), // You can adjust the size as needed
                                        animationResId = R.raw.loading_animation // Pass the animation resource
                                    )
                                },
                                failure = {
                                    Text(text = "Image failed to load")
                                }
                            )
                            CustomHeaderText(content = food.yemek_adi)

                           Spacer(modifier = Modifier.height(8.dp))

                            CustomText(
                                content = if (quantity <= 1) "Fiyat : ${food.yemek_fiyat} ₺"
                                else "Fiyat : ${String.format(totalPrice.toString())} ₺"
                            )


                            // Row to display minus icon, quantity, and plus icon
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = {
                                        if (quantity >= 1) quantity-- // Decrease quantity
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_remove_24),
                                        contentDescription = "Decrease quantity"
                                    )
                                }

                                Text(text = quantity.toString(), fontSize = 14.sp)

                                IconButton(
                                    onClick = {
                                        quantity++ // Increase quantity
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase quantity"
                                    )
                                }
                            }
                        }

                        // Heart icon at the top-right corner
                        IconButton(
                            onClick = {
                                if (isFavorite.value) {
                                    // Remove from favorites
                                    favoriteFoods.remove(food)
                                    isFavorite.value = false
                                } else {
                                    // Add to favorites
                                    favoriteFoods.add(food)
                                    isFavorite.value = true
                                }

                                // Save the updated favorites list
                                homeScreenViewModel.saveFavoriteFoods(favoriteFoods)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFavorite.value) "Remove from favorites" else "Add to favorites",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}




