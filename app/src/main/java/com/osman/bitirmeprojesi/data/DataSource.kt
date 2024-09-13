    package com.osman.bitirmeprojesi.data

    import android.content.Context
    import android.content.SharedPreferences
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.platform.LocalContext
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import dagger.hilt.android.qualifiers.ApplicationContext

    import com.google.gson.Gson
    import com.google.gson.reflect.TypeToken
    import com.osman.bitirmeprojesi.entity.Food
    import com.osman.bitirmeprojesi.retrofit.FoodDao
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.withContext
    import javax.inject.Inject


    class DataSource @Inject constructor(
        private val foodDao: FoodDao,
        @ApplicationContext private val context: Context
    ){

        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE)


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

        // TODO: fooddao ile ana ekranda tüm yemekleri göstermek istenilecek

        suspend fun loadAllFood() : List<Food> = withContext(Dispatchers.IO){

            return@withContext foodDao.loadAllFood().foodList

        }

        // Save favorite foods list to SharedPreferences
        fun saveFavoriteFoods(favoriteFoods: List<Food>) {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(favoriteFoods)
            editor.putString("favorite_foods_list", json)
            editor.apply()
        }

        // Retrieve favorite foods list from SharedPreferences
        fun getFavoriteFoods(): List<Food> {
            val gson = Gson()
            val json = sharedPreferences.getString("favorite_foods_list", null)
            return if (json != null) {
                val type = object : TypeToken<List<Food>>() {}.type
                gson.fromJson(json, type)
            } else {
                emptyList() // Return an empty list if there's no data
            }
        }




    }