package com.roshan.sample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.roshan.sample.MainApplication

/**
 * @Details AppUtils: Common Utility Class to handle the utils functions
 * @Author Roshan Bhagat
 */

/**
 * Is network connected check and return the n/w availability of user's device
 *
 * @param context
 * @return boolean true isNetworkConnected else false
 */
fun isNetworkConnected(): Boolean {
    val connectivityManager =
        MainApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
    ) || capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_VPN
    ))
}

/**
 * Show log
 *
 * @param tagName
 * @param message
 */
fun showLog(tagName: String?, message: String) {
    if (!TextUtils.isEmpty(message)) {
        val maxLogSize = message.length + 1
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length)
                message.length
            else end
            Log.v(tagName, message.substring(start, end))
        }
    }
}

/**
 * Log exception
 *
 * @param t
 */
fun logException(t: Throwable?) {
    Log.e("", Log.getStackTraceString(t))
}

/**
 * Is list not empty
 *
 * @param list
 */
fun isListNotEmpty(list: List<Any>?) = !(list?.isEmpty() ?: true)

/**
 * Is valid string
 *
 * @param value
 */
fun String?.isValidString() = !TextUtils.isEmpty(this)


/**
 * Navigate with Arguments
 *
 * @receiver [NavController]
 * @param route Route
 * @param args Args
 * @param navOptions Nav options
 * @param navigatorExtras Navigator extras
 */
fun NavController.navigateWithArgs(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}

