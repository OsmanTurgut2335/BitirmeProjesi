package com.osman.bitirmeprojesi.entity

// cart data class for the response from the api

data class CartFood(var sepet_yemek_id: String,
                    var yemek_adi :String,
                    var yemek_resim_adi:String,
                    var yemek_fiyat:String,
                    var yemek_siparis_adet:String,
                    var kullanici_adi :String) {

}