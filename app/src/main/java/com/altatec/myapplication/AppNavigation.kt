package com.altatec.myapplication

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altatec.myapplication.ui.view.LoginScreen
import com.altatec.myapplication.ui.view.RegisterScreen
import com.altatec.myapplication.ui.view.ScaffoldScreen
import com.altatec.myapplication.ui.Screen
import com.altatec.myapplication.ui.view.SplashScreen
import com.altatec.myapplication.ui.viewmodel.LoginViewModel
import com.altatec.myapplication.ui.viewmodel.RegisterViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen {
                navController.navigate(Screen.LoginScreen.route)
            }
        }
        composable(route = Screen.LoginScreen.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                loginViewModel,
                { navController.navigate(Screen.RegisterScreen.route) },
                { navController.navigate(Screen.ScaffoldScreen.route) }
            )
        }
        composable(route = Screen.RegisterScreen.route) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                registerViewModel
            ) { navController.navigate(Screen.LoginScreen.route) }
        }
        composable(route = Screen.ScaffoldScreen.route) {
            ScaffoldScreen()
        }
    }
}