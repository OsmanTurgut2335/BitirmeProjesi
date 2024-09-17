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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.DetailsScreenViewModel
import com.osman.bitirmeprojesi.views.customviews.Chip
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    food: Food,
    detailsScreenViewModel: DetailsScreenViewModel,
    navController: NavController
) {
    val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
    var quantity by remember { mutableStateOf(1) }  // Set default quantity to 1
    val price = food.yemek_fiyat.toDoubleOrNull() ?: 0.0
    val totalPrice = quantity * price

    // Snackbar state to manage message display
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        topBar = { TopAppBar(title = { Text(text = "Ürün Detayları") }) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display food details
                Text(text = "${food.yemek_adi}", modifier = Modifier.padding(bottom = 8.dp))

                GlideImage(
                    imageModel = { imageUrl },
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    loading = { CircularProgressIndicator(modifier = Modifier.size(40.dp)) },
                    failure = { Text(text = "Image failed to load") }
                )

                Text(text = "Fiyat ${totalPrice.toInt()} ₺", modifier = Modifier.padding(top = 8.dp))

                // Row to handle quantity selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_remove_24),
                            contentDescription = "Decrease quantity"
                        )
                    }

                    Text(text = quantity.toString())

                    IconButton(onClick = { quantity++ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase quantity")
                    }
                }

                // Button to add food to cart
                Chip(onClick = {
                    detailsScreenViewModel.addToCart(
                        food = food,
                        quantity = quantity,
                        kullaniciAdi = "osman_turgut", // Dummy username
                        onSuccess = { message -> snackbarMessage = message }, // Success callback
                        onFailure = { errorMessage -> snackbarMessage = errorMessage } // Failure callback
                    )
                },
                    content = "Sepete Ekle")
            }
        }
    )

    // Show Snackbar when a message is available
    snackbarMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message)
            snackbarMessage = null
        }
    }
}
