package com.osman.bitirmeprojesi.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DataSource {

    // TODO:  login işlevi öncelikli olarak yapılacak

    fun login(username: String, password: String, callback: (Result<FirebaseUser>) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        callback(Result.success(user))
                    } else {
                        callback(Result.failure(Exception("User is null after successful authentication")))
                    }
                } else {
                    callback(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }


}