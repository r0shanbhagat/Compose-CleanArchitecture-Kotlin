package com.roshan.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.roshan.sample.presentation.navigation.NavGraph
import com.roshan.sample.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    AppTheme {
        val navController = rememberNavController()
        NavGraph(navController)
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MainScreen()
}