package com.osman.bitirmeprojesi.views

import BottomNavigationBar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.entity.CartFood
import com.osman.bitirmeprojesi.viewmodels.PaymentScreenViewModel
import com.osman.bitirmeprojesi.views.customviews.AnimatedPreloader
import com.osman.bitirmeprojesi.views.customviews.CustomText
import com.osman.bitirmeprojesi.views.customviews.TopBarText
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
        topBar = { TopAppBar(  title = { TopBarText(title = "Sepetiniz") },
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
            }) },
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
                        AnimatedPreloader(modifier = Modifier.size(200.dp))
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
                            style = MaterialTheme.typography.titleLarge,
                            color = colorResource(id = R.color.textColor)
                        )
                    }
                }
            }
            if (showDialog && itemToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { CustomText(content = "Emin misiniz ?") },
                    text = {  CustomText(content = "Bunu silmek istediğinizden emin misiniz ?") },
                    confirmButton = {
                        Button(onClick = {
                            paymentScreenViewModel.deleteItemFromCart(itemToDelete!!)
                            showDialog = false
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.buttonBackground),  // Color inside the circle
                            contentColor = colorResource(id = R.color.bottomNavColor)    // Text color
                        ),
                            shape = CircleShape,  ) {
                            CustomText(content = "Sil ?")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.buttonBackground),  // Color inside the circle
                                contentColor = colorResource(id = R.color.bottomNavColor)    // Text color
                            ),
                            shape = CircleShape,  ) {
                            CustomText(content = "İptal")
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
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardColors(containerColor = colorResource(id = R.color.bottomNavColor), contentColor =colorResource(id = R.color.redBackground) ,
            disabledContentColor =colorResource(id = R.color.redBackground) , disabledContainerColor =colorResource(id = R.color.bottomNavColor) )
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
                 //   CircularProgressIndicator(modifier = Modifier.size(40.dp))
                    AnimatedPreloader(modifier = Modifier.size(200.dp))
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
                Text(text = item.yemek_adi, style = MaterialTheme.typography.titleMedium,color = colorResource(id = R.color.textColor))
              //  Text(text = "${item.yemek_fiyat} ₺", style = MaterialTheme.typography.bodyMedium,color = colorResource(id = R.color.textColor))
                CustomText(content = "${item.yemek_fiyat} ₺")
               // Text(text = "Quantity: ${item.yemek_siparis_adet}", style = MaterialTheme.typography.bodyMedium,color = colorResource(id = R.color.textColor))
                CustomText(content = "Adet: ${item.yemek_siparis_adet}")
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

