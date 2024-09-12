package com.osman.bitirmeprojesi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osman.bitirmeprojesi.viewmodels.LoginScreenViewModel
import com.osman.bitirmeprojesi.views.DetailsScreen
import com.osman.bitirmeprojesi.views.HomeScreen
import com.osman.bitirmeprojesi.views.LoginScreen


@Composable
fun Navigations(loginScreenViewModel: LoginScreenViewModel) {
   val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginscreen") {
        composable("loginscreen") {
           LoginScreen(navController, loginScreenViewModel)
        }
        composable("homeScreen") {
            HomeScreen()
        }
        composable("detailsScreen") {
            DetailsScreen()
        }
        composable("paymentScreen") {
            HomeScreen()
        }
    }


}