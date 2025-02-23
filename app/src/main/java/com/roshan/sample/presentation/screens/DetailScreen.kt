package com.roshan.sample.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.roshan.sample.domain.model.ProductDetail
import com.roshan.sample.presentation.state.StateEvent
import com.roshan.sample.presentation.viewmodel.ProductDetailVewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    productId: String,
    onBackPressed: () -> Unit,
) {
    val viewModel = koinViewModel<ProductDetailVewModel>()
    LaunchedEffect(Unit) {
        viewModel.setStateIntent(StateEvent.ProductDetails(productId))
    }
    val context = LocalContext.current
    val result = viewModel.productDetail.value

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
            ListDetailItem(it)
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

//    SystemBackButtonHandler {
//        onBackPressed.invoke()
//    }

}

@Composable
fun ListDetailItem(category: ProductDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
                    .weight(0.4f),
                painter = rememberAsyncImagePainter(category.image),
                contentDescription = ""
            )
            UserDescription(category, Modifier.weight(0.6f))
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
fun UserDescription(category: ProductDetail, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(category.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text(category.description, fontSize = 12.sp, maxLines = 4, overflow = TextOverflow.Ellipsis)
    }
}
