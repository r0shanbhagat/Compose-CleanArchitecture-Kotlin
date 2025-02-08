package com.roshan.sample.domain.repository

import com.roshan.sample.domain.model.ProductDetail
import com.roshan.sample.domain.model.ProductItem

interface Repository {

    suspend fun getProductList(): List<ProductItem>

    suspend fun getProductDetail(id: String): ProductDetail

}