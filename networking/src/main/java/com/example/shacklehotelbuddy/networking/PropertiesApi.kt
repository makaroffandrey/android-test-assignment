package com.example.shacklehotelbuddy.networking

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PropertiesApi {
    @POST("properties/v2/list")
    suspend fun getProperties(@Body request: PropertyListRequest): PropertyListResponse

}

interface PropertiesApiProvider {
    val propertiesApi: PropertiesApi
}
