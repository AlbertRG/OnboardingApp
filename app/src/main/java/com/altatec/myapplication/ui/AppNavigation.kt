package com.altatec.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altatec.myapplication.Screen

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
            //val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                { navController.navigate(Screen.RegisterScreen.route) },
                { navController.navigate(Screen.ScaffoldScreen.route) }
            )
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen() {
                navController.navigate(Screen.LoginScreen.route)
            }
        }
        composable(route= Screen.ScaffoldScreen.route){
            ScaffoldScreen(navController = navController)
        }
    }
}