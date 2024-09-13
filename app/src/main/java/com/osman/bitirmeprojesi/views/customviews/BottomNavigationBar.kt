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
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
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
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
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
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = currentRoute == "paymentScreen",
            onClick = {
                if (currentRoute != "paymentScreen") {
                    navController.navigate("paymentScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}
