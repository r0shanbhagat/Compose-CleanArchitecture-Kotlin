package com.roshan.sample.presentation.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roshan.sample.presentation.screens.DetailScreen
import com.roshan.sample.presentation.screens.ListingScreen
import com.roshan.sample.utils.navigateWithArgs

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.ProductListingScreen.route
    ) {
        productListingScreen(navController, this)
        productDetails(navController, this)
    }
}

private fun productListingScreen(
    navController: NavHostController, navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.ProductListingScreen.route
    ) {
        ListingScreen(detailScreen = { productId ->
            val args = Bundle().apply {
                putString("product-id", productId)
            }
            navController.navigateWithArgs(
                route = NavRoute.ProductDetailsScreen.route, args = args
            )
        })
    }
}

private fun productDetails(
    navController: NavHostController, navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.ProductDetailsScreen.route
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        val productId: String = args?.getString("product-id").toString()
        DetailScreen(productId = productId, onBackPressed = {
            popUpToListingScreen(navController)
        })
    }
}


private fun popUpToListingScreen(navController: NavHostController) {
    navController.popBackStack(NavRoute.ProductListingScreen.route, inclusive = false)
}
