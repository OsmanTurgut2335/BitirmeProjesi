package com.osman.bitirmeprojesi.repo

import com.google.firebase.auth.FirebaseUser
import com.osman.bitirmeprojesi.data.DataSource

class Repository (val dataSource: DataSource){

     fun login(username: String, password: String, callback: (Result<FirebaseUser>) -> Unit) {
        dataSource.login(username, password) { result ->
            callback(result)
        }
    }

}