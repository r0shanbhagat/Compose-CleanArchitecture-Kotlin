package com.roshan.sample.presentation.state

import com.roshan.sample.domain.model.ProductDetail

data class ProductDetailState(
    val isLoading: Boolean = false,
    val data: ProductDetail? = null,
    var error: String = ""
)
