package com.roshan.sample.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.roshan.sample.MainApplication
import com.roshan.sample.R
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.io.IOException
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.nio.charset.Charset
import java.util.*

@Serializable
data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)

fun readRawResourceFile(resourceId: Int): String {
    try {
        val inputStream = MainApplication.instance.resources.openRawResource(resourceId)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    } catch (e: IOException) {
        e.printStackTrace()
        return ""
    }
}

suspend fun getAccessToken(): String {
    val json = Json.parseToJsonElement(readRawResourceFile(R.raw.serviceaccount)).jsonObject
    val clientEmail = json["client_email"]!!.jsonPrimitive.content
    val privateKey = json["private_key"]!!.jsonPrimitive.content

    val now = System.currentTimeMillis() / 1000
    val jwt = JWT.create()
        .withIssuer(clientEmail)
        .withAudience("https://oauth2.googleapis.com/token")
        .withClaim("scope", "https://www.googleapis.com/auth/firebase.messaging")
        .withIssuedAt(Date(now * 1000))
        .withExpiresAt(Date((now + 3600) * 1000))
        .sign(Algorithm.RSA256(null, privateKey.toRSAPrivateKey()))

    val client = HttpClient(Android)
    val response = client.post("https://oauth2.googleapis.com/token") {
        contentType(ContentType.Application.FormUrlEncoded)
        setBody(
            listOf(
                "grant_type" to "urn:ietf:params:oauth:grant-type:jwt-bearer",
                "assertion" to jwt
            ).formUrlEncode()
        )
    }

    client.close()
    val tokenResponse = Json.decodeFromString<TokenResponse>(response.body())
    return tokenResponse.access_token
}

fun String.toRSAPrivateKey(): java.security.interfaces.RSAPrivateKey {
    val privateKeyPEM = this
        .replace("-----BEGIN PRIVATE KEY-----", "")
        .replace("-----END PRIVATE KEY-----", "")
        .replace("\\s".toRegex(), "")
    val keySpec = java.security.spec.PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM))
    val keyFactory = java.security.KeyFactory.getInstance("RSA")
    return keyFactory.generatePrivate(keySpec) as java.security.interfaces.RSAPrivateKey
}

suspend fun sendFCMDataNotification(
    projectId: String,
    deviceToken: String,
    title: String,
    body: String,
    data: Map<String, String>
) {
    val accessToken = getAccessToken()
    val client = HttpClient(Android)

    val messageJson = buildJsonObject {
        putJsonObject("message") {
            put("token", deviceToken)
            putJsonObject("notification") {
                put("title", title)
                put("body", body)
            }
            putJsonObject("data") {
                data.forEach { (key, value) ->
                    put(key, value)
                }
            }
        }
    }.toString()

    val response: HttpResponse = client.post("https://fcm.googleapis.com/v1/projects/$projectId/messages:send") {
        header("Authorization", "Bearer $accessToken")
        contentType(ContentType.Application.Json)
        setBody(messageJson)
    }

    println("FCM Response: ${response.status}")
    println(response.bodyAsText())
    client.close()
}
