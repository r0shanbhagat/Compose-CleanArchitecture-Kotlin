package com.roshan.sample.data.datasources.api

import com.roshan.sample.domain.model.ProductListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/products")
    suspend fun getAllProductListAPI(): List<ProductListDTO>

    @GET("/products/{Id}")
    suspend fun getProductDetailsAPI(@Path("Id") id: String): ProductListDTO

}