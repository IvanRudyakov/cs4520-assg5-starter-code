package com.cs4520.assignment5

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme

enum class Screen {
    PRODUCTS,
    LOGIN,
}
sealed class NavigationItem(val route: String) {
    object Products : NavigationItem(Screen.PRODUCTS.name)
    object Login : NavigationItem(Screen.LOGIN.name)
}

@Composable
fun AppNavHost(
    productListViewModel: ProductListViewModel,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.Login.route
) {
    val navController = rememberNavController()

    MaterialTheme() {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavigationItem.Products.route) {
                ItemsPage(navController, productListViewModel)
            }
            composable(NavigationItem.Login.route) {
                LoginScreen(navController, productListViewModel)
            }
        }
    }
}