package com.osman.bitirmeprojesi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.osman.bitirmeprojesi.entity.Food
import com.osman.bitirmeprojesi.viewmodels.DetailsScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.FavoritesScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.HomeScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.LoginScreenViewModel
import com.osman.bitirmeprojesi.viewmodels.PaymentScreenViewModel
import com.osman.bitirmeprojesi.views.DetailsScreen
import com.osman.bitirmeprojesi.views.FavoritesScreen
import com.osman.bitirmeprojesi.views.HomeScreen
import com.osman.bitirmeprojesi.views.LoginScreen
import com.osman.bitirmeprojesi.views.PaymentScreen


@Composable
fun Navigations(loginScreenViewModel: LoginScreenViewModel,homeScreenViewModel: HomeScreenViewModel,
                detailsScreenViewModel: DetailsScreenViewModel,favoritesScreenViewModel: FavoritesScreenViewModel,
                paymentScreenViewModel: PaymentScreenViewModel) {
   val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginscreen") {
        composable("loginscreen") {
            LoginScreen(navController, loginScreenViewModel)
        }
        composable("homeScreen") {
            HomeScreen(homeScreenViewModel,navController)
        }
        composable("detailsScreen/{foodJson}") { backStackEntry ->
            val foodJson = backStackEntry.arguments?.getString("foodJson")
            val food = Gson().fromJson(foodJson, Food::class.java) // Deserialize the JSON to a Food object
            DetailsScreen(food = food, detailsScreenViewModel,navController )
        }
       /* composable("paymentScreen/{foodJson}/{quantity}/{username}") { backStackEntry ->
            val foodJson = backStackEntry.arguments?.getString("foodJson")
            val quantity = backStackEntry.arguments?.getString("quantity")?.toInt() ?: 0
            val username = backStackEntry.arguments?.getString("username")
            val food = Gson().fromJson(foodJson, Food::class.java) // Deserialize the JSON to a Food object
            PaymentScreen(navController, food, quantity, username)
        }*/

        composable("paymentScreen/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            PaymentScreen(paymentScreenViewModel, navController)
        }

        composable("favoritesScreen") {
            FavoritesScreen(navController,favoritesScreenViewModel)
        }

    }


}