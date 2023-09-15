package com.example.shacklehotelbuddy

import android.app.Application
import com.example.shacklehotelbuddy.di.ServiceLocator
import com.example.shacklehotelbuddy.networking.NetworkingModule
import com.example.shacklehotelbuddy.networking.PropertiesApi
import com.example.shacklehotelbuddy.networking.PropertiesApiProvider

class ShackleHotelBuddyApplication : Application(), PropertiesApiProvider {

    private lateinit var serviceLocator: ServiceLocator
    override fun onCreate() {
        super.onCreate()
        val networkingModule = NetworkingModule(
            apiUrl = BuildConfig.HOTEL_API_URL,
            apiKey = BuildConfig.HOTEL_API_KEY,
            enableLogging = BuildConfig.DEBUG,
        )
        serviceLocator = ServiceLocator(networkingModule)
    }

    override val propertiesApi: PropertiesApi
        get() = serviceLocator.propertiesApi
}
