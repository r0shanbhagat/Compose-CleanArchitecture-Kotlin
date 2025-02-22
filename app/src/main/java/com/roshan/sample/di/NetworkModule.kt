package com.roshan.sample.di

import android.util.Log
import com.roshan.sample.data.datasources.api.ApiService
import com.roshan.sample.data.datasources.api.ApiServiceImpl
import com.roshan.sample.utils.Constant.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * @Details NetworkModule
 * @Author Roshan Bhagat
 * @constructor Create Network module
 */

fun provideHttpClient(): HttpClient = HttpClient(Android) {
    //Header
    install(DefaultRequest) {
        accept(ContentType.Application.Json)
        contentType(ContentType.Application.Json)
        url(BASE_URL)
        url {
            // parameters.append("api_key", .API_KEY)
        }
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        })
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Network", message)
            }

        }
        level = LogLevel.ALL
    }
}

val networkModule = module {
    single<HttpClient> { provideHttpClient() }
    single<ApiService> { ApiServiceImpl(get()) }

}