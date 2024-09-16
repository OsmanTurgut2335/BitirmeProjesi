package com.osman.bitirmeprojesi.entity

import com.google.gson.annotations.SerializedName

// A custom response class for adding food to the cart
// also used for removing an item from cart - same answer from api

data class AddToCartResponse(
    @SerializedName("success") val success: Int,
    @SerializedName("message") val message: String?
)
