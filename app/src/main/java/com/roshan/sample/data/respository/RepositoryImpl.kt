package com.roshan.sample.data.respository

import com.roshan.sample.data.datasources.api.ApiService
import com.roshan.sample.domain.model.ProductDetail
import com.roshan.sample.domain.model.ProductItem
import com.roshan.sample.domain.repository.Repository
import com.roshan.sample.domain.toProductDetail
import com.roshan.sample.domain.toProductList


class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun getProductList(): List<ProductItem> {
        return apiService.getAllProductListAPI().map { it.toProductList() }
    }

    override suspend fun getProductDetail(id: String): ProductDetail {
        return apiService.getProductDetailsAPI(id).toProductDetail()
    }

}