package com.osman.bitirmeprojesi.repo

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import com.google.firebase.auth.FirebaseUser
import com.osman.bitirmeprojesi.data.DataSource
import com.osman.bitirmeprojesi.entity.Food

class Repository (var dataSource: DataSource){

     fun login(username: String, password: String, callback: (Result<FirebaseUser>) -> Unit) {
        dataSource.login(username, password) { result ->
            callback(result)
        }
    }

    suspend fun loadAllFood() : List<Food> = dataSource.loadAllFood()




    // Save favorite foods through the DataSource
    fun saveFavoriteFoods(favoriteFoods: List<Food>) {
        dataSource.saveFavoriteFoods(favoriteFoods)
    }

    // Retrieve favorite foods through the DataSource
    fun getFavoriteFoods(): List<Food> {
        return dataSource.getFavoriteFoods()
    }
}