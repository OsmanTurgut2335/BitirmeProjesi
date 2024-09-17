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
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    // Get the current route
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Ana Ekran") },
            label = { Text("Ana Ekran") },
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
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoriler") },
            label = { Text("Favoriler") },
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
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Sepet") },
            label = { Text("Sepet") },
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
