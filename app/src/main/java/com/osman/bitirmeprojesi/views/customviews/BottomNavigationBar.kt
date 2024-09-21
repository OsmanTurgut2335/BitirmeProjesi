import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.views.customviews.CustomText

@Composable
fun BottomNavigationBar(navController: NavController) {
    // Get the current route
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar (containerColor = colorResource(id = R.color.bottomNavColor)){
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Ana Ekran",
                tint = colorResource(id = R.color.redBackground
            )) },
            label = { CustomText(content = "Ana Ekran") },
            selected = currentRoute == "homeScreen",
            onClick = {
                if (currentRoute != "homeScreen") {
                    navController.navigate("homeScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoriler" ,
                tint = colorResource(id = R.color.redBackground
                    ))},
            label = { CustomText(content = "Favoriler") },
            selected = currentRoute == "favoritesScreen",
            onClick = {
                if (currentRoute != "favoritesScreen") {
                    navController.navigate("favoritesScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
        /*
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profileScreen",
            onClick = {
                if (currentRoute != "profileScreen") {
                    navController.navigate("profileScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )*/

        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Sepet" ,
                tint = colorResource(id = R.color.redBackground
                    )) },
            label = { CustomText(content = "Sepet") },
            selected = currentRoute == "paymentScreen",
            onClick = {
                val username = "osman_turgut" // Replace with your actual username variable
                if (currentRoute != "paymentScreen") {
                    navController.navigate("paymentScreen/$username") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

    }
}
