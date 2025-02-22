package com.roshan.sample.data.datasources.api

import com.roshan.sample.domain.model.ProductListDTO

/**
 * @Details ApiService
 * @Author Roshan Bhagat
 * @constructor Create Api service
 */
interface ApiService {
    /**
     * Performs a GET call to obtain a getAllProductListAPI
     */
    suspend fun getAllProductListAPI(): List<ProductListDTO>

    /**
     * Base on Movies Id fetch the details of movie
     * @param id: String id of product based on˚ which product should be fetched
     * @return
     */
    suspend fun getProductDetailsAPI(id: String): ProductListDTO
}
