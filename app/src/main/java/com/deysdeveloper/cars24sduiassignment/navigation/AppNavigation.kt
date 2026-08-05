package com.deysdeveloper.cars24sduiassignment.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deysdeveloper.cars24sduiassignment.ui.screen.HomeScreen

object Routes {
    const val HOME = "home"
    const val BUY_USED_CAR = "buy_used_car"
    const val SELL_CAR = "sell_car"
    const val LOANS = "loans"
    const val CAR_DETAIL = "car_detail"
    const val SHOWROOM_DETAIL = "showroom_detail"
    const val SERVICE_DETAIL = "service_detail"
    const val SEARCH = "search"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(Routes.BUY_USED_CAR) { PlaceholderScreen("Buy Used Car") }
        composable(Routes.SELL_CAR) { PlaceholderScreen("Sell Car") }
        composable(Routes.LOANS) { PlaceholderScreen("Loans") }
        composable(Routes.CAR_DETAIL) { PlaceholderScreen("Car Detail") }
        composable(Routes.SHOWROOM_DETAIL) { PlaceholderScreen("Showroom Detail") }
        composable(Routes.SERVICE_DETAIL) { PlaceholderScreen("Service Detail") }
        composable(Routes.SEARCH) { PlaceholderScreen("Search") }
    }
}

/** Safe fallback so unknown navigate destinations never crash. */
fun NavController.navigateSafe(route: String) {
    val validRoutes = setOf(
        Routes.HOME, Routes.BUY_USED_CAR, Routes.SELL_CAR,
        Routes.LOANS, Routes.CAR_DETAIL, Routes.SHOWROOM_DETAIL,
        Routes.SERVICE_DETAIL, Routes.SEARCH
    )
    if (route in validRoutes) navigate(route)
}

@Composable
private fun PlaceholderScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = name)
    }
}
