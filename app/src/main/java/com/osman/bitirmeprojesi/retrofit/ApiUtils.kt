package com.osman.bitirmeprojesi.retrofit

import com.osman.bitirmeprojesi.entity.Food

class ApiUtils {
    // interface' e erişmek
    // her bir interface için bir fonksiyon

    companion object{
        val ALL_FOOD_BASE_URL = "http://kasimadalan.pe.hu/"

        fun getFoodDao():FoodDao {
            return RetrofitClient.getClient(ALL_FOOD_BASE_URL).create(FoodDao::class.java)
        }
    }


}