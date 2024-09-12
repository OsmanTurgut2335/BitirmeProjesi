package com.osman.bitirmeprojesi.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.DetailsScreenViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailsScreen(food: Food,detailsScreenViewModel: DetailsScreenViewModel,navController: NavController) {
    // Construct the full image URL
    val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the food name
        Text(text = "Details of ${food.yemek_adi}", modifier = Modifier.padding(bottom = 8.dp))

        // Display the image using Glide
        GlideImage(
            imageModel = { imageUrl },
            modifier = Modifier
                .height(200.dp) // Adjust height as needed
                .fillMaxWidth(),
            loading = {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            },
            failure = {
                Text(text = "Image failed to load")
            }
        )

        // Display additional details of the food
        Text(text = "Price: ${food.yemek_fiyat}", modifier = Modifier.padding(top = 8.dp))
        // Add more details if needed
    }
}
