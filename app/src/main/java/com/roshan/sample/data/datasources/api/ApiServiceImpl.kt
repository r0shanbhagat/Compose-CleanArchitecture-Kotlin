package com.roshan.sample.data.datasources.api

import com.roshan.sample.domain.model.ProductListDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


/**
 * @Details :ApiServiceImpl
 * @Author Roshan Bhagat
 */
class ApiServiceImpl(private val client: HttpClient) : ApiService {
    companion object {
        const val PRODUCT = "/products"
        const val PRODUCT_DETAIL = "/products/%s"
    }

    override suspend fun getAllProductListAPI(): List<ProductListDTO> =
        client.get(PRODUCT).body()

    override suspend fun getProductDetailsAPI(id: String): ProductListDTO =
        client.get(PRODUCT_DETAIL.format(id)).body()

}