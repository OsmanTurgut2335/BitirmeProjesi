package com.osman.bitirmeprojesi.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osman.bitirmeprojesi.entity.AddToCartResponse
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.retrofit.ApiUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor() : ViewModel() {

    fun addToCart(
        food: Food,
        quantity: Int,
        kullaniciAdi: String,
        onSuccess: (String) -> Unit,  // Success callback
        onFailure: (String) -> Unit   // Failure callback
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foodDao = ApiUtils.getFoodDao()
                Log.d("AddToCart", "Request params: $food, $quantity, $kullaniciAdi")

                // Call the API using Retrofit
                val response = foodDao.addToCart(
                    yemekAdi = food.yemek_adi,
                    yemekResimAdi = food.yemek_resim_adi,
                    yemekFiyat = food.yemek_fiyat.toInt(),
                    yemekSiparisAdet = quantity,
                    kullaniciAdi = kullaniciAdi
                )

                // Check the response
                if (response.success == 1) {
                    onSuccess("Sepete eklendi!")
                } else {
                    onFailure("Failed to add item to the cart. Response success value: ${response.success}")
                }

            } catch (e: Exception) {
                Log.e("AddToCart", "Exception: ${e.message}")
                onFailure("Error: ${e.message}")
            }
        }
    }
}
