package com.deysdeveloper.cars24sduiassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deysdeveloper.cars24sduiassignment.ui.screen.HomeScreen

private const val ROUTE_HOME = "home"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ROUTE_HOME) {
        composable(ROUTE_HOME) { HomeScreen() }
    }
}
