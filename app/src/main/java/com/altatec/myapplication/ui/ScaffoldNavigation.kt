package com.altatec.myapplication.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.altatec.myapplication.Screen

@Composable
fun ScaffoldNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.BottomScreen.Home.bRoute
    ) {
        composable(route = Screen.BottomScreen.Home.bRoute) {
            HomeScreen()
        }
        composable(route = Screen.BottomScreen.Contacts.bRoute) {
            ContactsScreen()
        }
        composable(route = Screen.BottomScreen.Api.bRoute) {
            ApiScreen()
        }
    }
}