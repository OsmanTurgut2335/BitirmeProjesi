package com.osman.bitirmeprojesi.repo

import com.google.firebase.auth.FirebaseUser
import com.osman.bitirmeprojesi.data.DataSource
import com.osman.bitirmeprojesi.entity.Food

class Repository (val dataSource: DataSource){

     fun login(username: String, password: String, callback: (Result<FirebaseUser>) -> Unit) {
        dataSource.login(username, password) { result ->
            callback(result)
        }
    }

    suspend fun loadAllFood() : List<Food> = dataSource.loadAllFood()


}