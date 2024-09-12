package com.osman.bitirmeprojesi.retrofit

import com.osman.bitirmeprojesi.entity.GetFoodResponse
import retrofit2.http.GET

interface FoodDao {
  //  http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php    fetch all the foods
    //http://kasimadalan.pe.hu/    base url
    //yemekler/tumYemekleriGetir.php  api url

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun loadAllFood() : GetFoodResponse



}