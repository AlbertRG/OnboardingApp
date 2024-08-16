package com.altatec.myapplication.ui

import androidx.annotation.DrawableRes
import com.altatec.myapplication.R

sealed class Screen(val route: String) {

    data object SplashScreen : Screen("splash_screen")
    data object LoginScreen : Screen("login_screen")
    data object RegisterScreen : Screen("register_screen")
    data object ScaffoldScreen : Screen("scaffold_screen")

    sealed class BottomScreen(val bRoute: String, val bTitle: String, @DrawableRes val icon: Int):
        Screen(bRoute) {
        data object Home : BottomScreen("home_screen", "Home", R.drawable.baseline_home_24)
        data object Contacts : BottomScreen("contacts_screen", "Contacts",
            R.drawable.baseline_contacts_24
        )
        data object Api : BottomScreen("api_screen", "Api", R.drawable.baseline_api_24)
    }

}

val screensInBottom = listOf(
    Screen.BottomScreen.Contacts,
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Api
)