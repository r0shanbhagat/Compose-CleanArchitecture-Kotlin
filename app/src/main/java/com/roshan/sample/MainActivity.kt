package com.roshan.sample

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.roshan.sample.presentation.navigation.NavGraph
import com.roshan.sample.ui.theme.AppTheme
import com.roshan.sample.utils.shouldShowNotificationDialog


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
        NotificationPermissionDialog()
        NavGraph(navController)
    }
}

@Composable
fun NotificationPermissionDialog() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(shouldShowNotificationDialog(context)) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            showDialog = false
        }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enable Notifications") },
            text = { Text("This app would like to send you notifications. Please allow notification permission.") },
            confirmButton = {
                Button(onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        showDialog = false
                    }
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Deny")
                }
            }
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MainScreen()
}