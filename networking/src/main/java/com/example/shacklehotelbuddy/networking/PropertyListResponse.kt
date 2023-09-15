package com.example.shacklehotelbuddy.networking

data class PropertyListResponse(
    val data: HotelListResponseData,
)

data class HotelListResponseData(
    val properties: List<Property>,
)

data class Property(
    val id: String,
    val name: String,
    val reviews: PropertyReviews?,
    val neighbourhood: PropertyNeighbourhood?,
)

data class PropertyImage(
    val image: UrlWithDescription?,
)

data class UrlWithDescription(
    val description: String?,
    val url: String,
)

data class PropertyReviews(
    val score: Float?,
    val total: Int?,
)

data class PropertyNeighbourhood(
    val name: String?,
)
