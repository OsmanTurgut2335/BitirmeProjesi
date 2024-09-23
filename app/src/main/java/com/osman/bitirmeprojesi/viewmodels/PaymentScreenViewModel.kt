package com.osman.bitirmeprojesi.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osman.bitirmeprojesi.entity.CartFood
import com.osman.bitirmeprojesi.entity.GetCartResponse
import com.osman.bitirmeprojesi.repo.Repository
import com.osman.bitirmeprojesi.retrofit.ApiUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    // StateFlow for cart food list
    private val _cartFoodList = MutableStateFlow<List<CartFood>>(emptyList())
    val cartFoodList: StateFlow<List<CartFood>> = _cartFoodList

    private val cartFoodFinal = MutableStateFlow<List<CartFood>>(emptyList())
    val cartItemsFinal: StateFlow<List<CartFood>> = cartFoodFinal

    // StateFlow for tracking loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // StateFlow for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {

        fetchCartItems("osman_turgut")
    }


    fun fetchCartItems(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val foodDao = ApiUtils.getFoodDao()
                val response: GetCartResponse = foodDao.getFoodFromCart(username)

                if (response.success == 1) {
                    val cartItems = response.cartFoodList
                    _cartFoodList.value = cartItems

                    // Aggregate items with the same food name
                    val aggregatedItems = cartItems.groupBy { it.yemek_adi }
                        .map { (foodName, items) ->
                            val totalQuantity = items.sumOf { it.yemek_siparis_adet.toInt() }
                            val totalPrice = items.sumOf { it.yemek_fiyat.toInt() * it.yemek_siparis_adet.toInt() }
                            val firstItem = items.first()

                            // Create a new CartFood item with the total quantity and total price
                            CartFood(
                                sepet_yemek_id = firstItem.sepet_yemek_id,
                                yemek_adi = firstItem.yemek_adi,
                                yemek_resim_adi = firstItem.yemek_resim_adi,
                                yemek_fiyat = totalPrice.toString(),
                                yemek_siparis_adet = totalQuantity.toString(),
                                kullanici_adi = firstItem.kullanici_adi
                            )
                        }

                    // Update the cartItemsFinal list with aggregated items
                    cartFoodFinal.value = aggregatedItems
                } else {
                    _cartFoodList.value = emptyList()
                    _errorMessage.value = "Failed to load cart items"
                }
            } catch (e: Exception) {
                // Log any error that occurs during the API call
                Log.e("PaymentScreen", "Error fetching cart items: ${e.message}")
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteItemFromCart(cartFood: CartFood) {
        // Find all items in the original list with the same food name (yemek_adi)
        val itemsToDelete = _cartFoodList.value.filter { it.yemek_adi == cartFood.yemek_adi }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foodDao = ApiUtils.getFoodDao()

                // Delete each item from the cart by its sepet_yemek_id
                for (item in itemsToDelete) {
                    foodDao.removeFromCart(item.sepet_yemek_id.toInt(), item.kullanici_adi)
                }

                // Fetch the cart items again to refresh the list
                fetchCartItems(cartFood.kullanici_adi)

            } catch (e: Exception) {
                // Handle the error
                println("Error deleting item: ${e.message}")
            }
        }
    }
}



