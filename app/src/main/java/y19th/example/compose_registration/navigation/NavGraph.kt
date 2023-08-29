package y19th.example.compose_registration.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import y19th.example.compose_registration.screens.MainScreen
import y19th.example.compose_registration.screens.ProfileScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.Main.route,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable(route = Screens.Main.route) {
            MainScreen(navigateForward = {
                navController.navigate(Screens.Profile.route)
            })
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }
    }
}