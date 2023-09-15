package com.example.shacklehotelbuddy.networking

import java.time.LocalDate

data class PropertyListRequest(
    val checkInDate: RequestDate,
    val checkOutDate: RequestDate,
    val rooms: List<PropertyRoom>,
)

data class RequestDate(
    val day: Int,
    val month: Int,
    val year: Int,
) {
    constructor(localDate: LocalDate) : this(
        day = localDate.dayOfMonth,
        month = localDate.monthValue,
        year = localDate.year,
    )

    fun asLocalDate(): LocalDate = LocalDate.of(
        year,
        month,
        day,
    )
}

data class PropertyRoom(val adults: Int)

data class Child(val age: Int)
