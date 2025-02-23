package com.roshan.sample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.roshan.sample.presentation.component.listItem
import com.roshan.sample.presentation.viewmodel.ProductListVewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListingScreen(detailScreen: (String) -> Unit) {
    val viewModel = koinViewModel<ProductListVewModel>()
    val context = LocalContext.current
    val result = viewModel.productList.value

    if (result.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }

    }

    result.data?.let {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(it) { item ->
                    listItem(item) {
                        detailScreen.invoke(item.id.toString())
                    }
                }
            }
        }
    }

    if (result.error.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = result.error.toString())
        }
    }

}