package presentation.screens.auth.navigation


sealed class AuthScreens(val route: String) {
    object SplashScreen : AuthScreens("splash_screen")
    object LoginScreen : AuthScreens("login_screen")
    object RegisterScreen : AuthScreens("register_screen")
}