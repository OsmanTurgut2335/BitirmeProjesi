package com.osman.bitirmeprojesi.views

import BottomNavigationBar
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Alignment
import com.osman.bitirmeprojesi.entity.CartFood
import com.osman.bitirmeprojesi.viewmodels.PaymentScreenViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(paymentScreenViewModel: PaymentScreenViewModel, navController: NavController) {

    val cartItems by paymentScreenViewModel.cartFoodList.collectAsState()

    val cartItemsFinal by paymentScreenViewModel.cartItemsFinal.collectAsState()  // organized list that arranges the same items added to the cart.

    // Collect loading state from the ViewModel's StateFlow
    val isLoading by paymentScreenViewModel.isLoading.collectAsState()

    // Collect error message from the ViewModel's StateFlow
    val errorMessage by paymentScreenViewModel.errorMessage.collectAsState()

    // Fetch cart items when the screen is launched
    LaunchedEffect(Unit) {
        paymentScreenViewModel.fetchCartItems("osman_turgut")
    }


    Scaffold(
        topBar = { TopAppBar(title = { Text("Cart Items") }) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        when {
            isLoading -> {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = errorMessage ?: "Unknown error", style = MaterialTheme.typography.bodyLarge)
                }
            }
            cartItems.isEmpty() -> {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Sepetiniz Boş", style = MaterialTheme.typography.bodyLarge)
                }
            }
            else -> {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(cartItemsFinal) { item ->
                        CartItemCard(item, onDeleteClick={
                            paymentScreenViewModel.deleteItemFromCart(item)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartFood , onDeleteClick : ()->Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = { "http://kasimadalan.pe.hu/yemekler/resimler/${item.yemek_resim_adi}" },
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                loading = {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                },
                failure = {
                    Text(text = "Image failed to load")
                }
            )
            Text(text = item.yemek_adi, style = MaterialTheme.typography.titleMedium)
            Text(text = "${item.yemek_fiyat} ₺", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantity: ${item.yemek_siparis_adet}", style = MaterialTheme.typography.bodyMedium)
            Button(onClick = onDeleteClick) {
                Text(text ="Delete item")
            }

        }
    }
}
