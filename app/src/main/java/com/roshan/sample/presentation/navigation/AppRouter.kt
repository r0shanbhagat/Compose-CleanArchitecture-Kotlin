package com.roshan.sample.presentation.navigation

sealed class NavRoute(val route: String) {
    data object ProductListingScreen : NavRoute("ProductListingScreen")
    data object ProductDetailsScreen : NavRoute("ProductDetailsScreen")
}