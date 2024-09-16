package com.osman.bitirmeprojesi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    //class dönüşümünü sağlayan sınıf

    companion object{
        fun getClient(baseUrl : String) : Retrofit{
            return Retrofit.Builder().
            baseUrl(baseUrl).
            addConverterFactory(GsonConverterFactory.create()).
            build()
        }   
    }



}