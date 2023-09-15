package com.example.shacklehotelbuddy.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkingModule(
    private val apiUrl: String,
    private val apiKey: String,
    private val enableLogging: Boolean,
) {
    private val headerInterceptor: PropertiesApiHeaderInterceptor by lazy {
        PropertiesApiHeaderInterceptor(
            apiKey = apiKey,
            apiHost = apiUrl,
        )
    }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .run {
                if (enableLogging) {
                    addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
                } else {
                    this
                }
            }
            .addInterceptor(headerInterceptor)
            .build()
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://$apiUrl")
            .build()
    }

    val propertiesApi: PropertiesApi by lazy {
        retrofit.create(PropertiesApi::class.java)
    }
}
