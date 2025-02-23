package com.roshan.sample.di


import com.roshan.sample.BuildConfig
import com.roshan.sample.data.datasources.api.ApiService
import com.roshan.sample.data.datasources.api.ApiServiceImpl
import com.roshan.sample.utils.NoInternetException
import com.roshan.sample.utils.isNetworkConnected
import com.roshan.sample.utils.showLog
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import org.koin.dsl.module

/**
 * @Details NetworkModule for Project
 * @Author Roshan Bhagat
 * @constructor Create Network module
 */

val networkKoinModule = module {
    single { provideKtorClient() }
    single { provideApiService(get()) }
}

/**
 * Provide ktor client.
 * @return : HttpClient
 */
private fun provideKtorClient(): HttpClient {
    val ktorClient = HttpClient(Android) {
        enableLogging(this)
        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 60000L
            connectTimeoutMillis = 60000L
            socketTimeoutMillis = 60000L
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = false
                ignoreUnknownKeys = true
            })
        }

        install(ResponseObserver) {
            onResponse { response ->
                showLog("Response:", response.toString())
            }
        }

//    //Header
        install(DefaultRequest) {
            url(BuildConfig.BASE_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

    }

    //Adding Interceptor
    ktorClient.addInterceptor()
    return ktorClient
}

private fun HttpClient.addInterceptor() {
    plugin(HttpSend).intercept { request ->
        if (!isNetworkConnected()) {
            throw NoInternetException(
                "", Throwable(NoInternetException::class.java.toString())
            )
        } else {
            execute(request)
        }
    }
}

private fun enableLogging(httpClientConfig: HttpClientConfig<AndroidEngineConfig>) {
    httpClientConfig.install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                showLog("KtorClient:", message)
            }
        }
        level = if (BuildConfig.DEBUG) {
            LogLevel.ALL
        } else {
            LogLevel.NONE
        }
    }
}

private fun setupSSLPinningWithAndroid() {
    HttpClient(Android) {
        engine {
            sslManager = { httpsURLConnection ->
                //  httpsURLConnection.sslSocketFactory = SslSettings.getSslContextFromPin()?.socketFactory
            }
        }
    }
}

private fun setupSSLPinningWithOkHttp(): HttpClient {
    val hostname = "sha256.badssl.com"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=").build()

    return HttpClient(OkHttp) {
        engine {
            config {
                certificatePinner(certificatePinner)
//                sslSocketFactory(
//                    SslSettings.getSslContextFromPin()!!.socketFactory,
//                    SslSettings.getTrustManager()
//                )
            }
        }
    }

}

/**
 * Provide api service.
 *
 * @param client Client
 */
private fun provideApiService(client: HttpClient): ApiService = ApiServiceImpl(client)
