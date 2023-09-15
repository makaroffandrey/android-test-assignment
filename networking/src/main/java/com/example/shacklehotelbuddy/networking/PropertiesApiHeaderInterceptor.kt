package com.example.shacklehotelbuddy.networking

import okhttp3.Interceptor
import okhttp3.Response

class PropertiesApiHeaderInterceptor(
    private val apiKey: String,
    private val apiHost: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(HEADER_API_KEY, apiKey)
            .addHeader(HEADER_API_HOST, apiHost)
            .build()
        return chain.proceed(newRequest)
    }
}

private const val HEADER_API_KEY = "X-RapidAPI-Key"
private const val HEADER_API_HOST = "X-RapidAPI-Host"
