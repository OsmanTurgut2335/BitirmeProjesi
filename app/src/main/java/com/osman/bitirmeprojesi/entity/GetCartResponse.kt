package com.osman.bitirmeprojesi.entity

import com.google.gson.annotations.SerializedName

//api response for getting the cart foods

data class GetCartResponse(
    @SerializedName("sepet_yemekler") var cartFoodList: List<CartFood>,
    var success: Int
)


