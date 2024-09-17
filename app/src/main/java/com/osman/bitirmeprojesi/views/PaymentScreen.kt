package com.osman.bitirmeprojesi.views

import BottomNavigationBar
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.osman.bitirmeprojesi.R
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

// Manage alert dialog visibility state
    var showDialog by remember { mutableStateOf(false) }
    var itemToDelete: CartFood? by remember { mutableStateOf(null) }


    // Fetch cart items when the screen is launched
    LaunchedEffect(Unit) {
        paymentScreenViewModel.fetchCartItems("osman_turgut")
    }
    // Calculate the total price of the cart items
    val totalPrice = cartItemsFinal.fold(0) { acc, item ->
        val price = item.yemek_fiyat.toIntOrNull() ?: 0
        acc + price
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Cart Items") }) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding from the Scaffold
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Unknown error",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                cartItems.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sepetiniz Boş", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Allow LazyColumn to take up available space
                    ) {
                        items(cartItemsFinal) { item ->
                            CartItemCard(item, onDeleteClick = {
                                itemToDelete = item
                                showDialog = true
                            })
                        }
                    }

                    // Total price text above the BottomNavigationBar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Toplam: $totalPrice ₺",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            if (showDialog && itemToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Are you sure?") },
                    text = { Text("Do you really want to delete this item?") },
                    confirmButton = {
                        Button(onClick = {
                            paymentScreenViewModel.deleteItemFromCart(itemToDelete!!)
                            showDialog = false
                        }) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartFood, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // Align items vertically
            horizontalArrangement = Arrangement.SpaceBetween // Arrange items with space between
        ) {
            // Image on the left
            GlideImage(
                imageModel = { "http://kasimadalan.pe.hu/yemekler/resimler/${item.yemek_resim_adi}" },
                modifier = Modifier
                    .size(90.dp), // Fixed size for the image
                loading = {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                },
                failure = {
                    Text(text = "Image failed to load")
                }
            )

            // Spacer to create space between image and text content
            Spacer(modifier = Modifier.width(60.dp))

            // Column for text content
            Column(
                modifier = Modifier.weight(1f), // Take up remaining space in the row
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = item.yemek_adi, style = MaterialTheme.typography.titleMedium)
                Text(text = "${item.yemek_fiyat} ₺", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Quantity: ${item.yemek_siparis_adet}", style = MaterialTheme.typography.bodyMedium)
            }

            // IconButton for deleting the item on the far right
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "remove item"
                )
            }
        }
    }
}

/*
 // Calculate the total price of the cart items
    val totalPrice = cartItemsFinal.fold(0f) { acc, item ->
        val price = item.yemek_fiyat.toFloatOrNull() ?: 0f
        val quantity = item.yemek_siparis_adet.toFloatOrNull() ?: 0f
        acc + (price * quantity)
    }
 */