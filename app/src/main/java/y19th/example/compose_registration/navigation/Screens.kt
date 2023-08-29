package y19th.example.compose_registration.navigation

sealed class Screens(val route: String) {
    object Main: Screens(route = "main_screen")
    object Profile: Screens(route = "profile_screen")
}