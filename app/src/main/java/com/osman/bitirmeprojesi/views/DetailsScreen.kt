package com.osman.bitirmeprojesi.views

import BottomNavigationBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.DetailsScreenViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(food: Food,detailsScreenViewModel: DetailsScreenViewModel,navController: NavController) {
    // Construct the full image URL
    val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"

    // Manage the quantity state using remember
    var quantity by remember { mutableStateOf(0) } // Initial quantity set to 0

// Convert price from String to Double for calculation
    val price = food.yemek_fiyat.toDoubleOrNull() ?: 0.0
    val totalPrice = quantity * price

  Scaffold(bottomBar = { BottomNavigationBar(navController = navController) },
      topBar = { TopAppBar(title = { Text(text = "Ürün Detayları") }) }) { paddingValues ->
      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
          // Display the food name
          Text(text = "Details of ${food.yemek_adi}", modifier = Modifier.padding(bottom = 8.dp))

          // Display the image using Glide
          GlideImage(
              imageModel = { imageUrl },
              modifier = Modifier
                  .height(100.dp) // Adjust height as needed
                  .width(100.dp),
              loading = {
                  CircularProgressIndicator(modifier = Modifier.size(40.dp))
              },
              failure = {
                  Text(text = "Image failed to load")
              }
          )

          // Display additional details of the food
          Text(text = "Fiyat ${String.format("%.2f", totalPrice)} ₺",modifier = Modifier.padding(top = 8.dp))
          // Add more details if needed
          Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center,
              modifier = Modifier.fillMaxWidth()
          ) {
              IconButton(
                  onClick = {
                      if (quantity > 1) quantity-- // Decrease quantity
                  }
              ) {
                  Icon(
                      imageVector = Icons.Default.Clear,
                      contentDescription = "Decrease quantity"
                  )
              }

              Text(text = quantity.toString())

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
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sepete Ekle")
        }
      }

  }
}
