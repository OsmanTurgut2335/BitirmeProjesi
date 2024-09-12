package com.osman.bitirmeprojesi.entity

import com.google.gson.annotations.SerializedName

//api response

data class GetFoodResponse (@SerializedName("yemekler") var foodList: List<Food> ,var success:Int){

}