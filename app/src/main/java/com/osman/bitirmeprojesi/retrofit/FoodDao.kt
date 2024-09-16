package com.osman.bitirmeprojesi.retrofit

import com.osman.bitirmeprojesi.entity.AddToCartResponse
import com.osman.bitirmeprojesi.entity.GetCartResponse
import com.osman.bitirmeprojesi.entity.GetFoodResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDao {
  //  http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php    fetch all the foods
    //http://kasimadalan.pe.hu/    base url
    //yemekler/tumYemekleriGetir.php  api url

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun loadAllFood() : GetFoodResponse

    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun addToCart(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekSiparisAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ):  AddToCartResponse

    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    suspend fun getFoodFromCart(
        @Field("kullanici_adi") kullaniciAdi: String
    ): GetCartResponse

    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    suspend fun removeFromCart(
        @Field("sepet_yemek_id") sepet_yemek_id: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): AddToCartResponse


}