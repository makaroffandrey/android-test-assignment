package com.example.shacklehotelbuddy.di

import com.example.shacklehotelbuddy.networking.NetworkingModule
import com.example.shacklehotelbuddy.networking.PropertiesApi

class ServiceLocator(module: NetworkingModule) {
    val propertiesApi = module.propertiesApi
}
