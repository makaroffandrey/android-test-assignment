package com.example.shacklehotelbuddy.common

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Immutable
data class SearchParams(
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val adultsNumber: Int,
    val childrenNumber: Int,
) : Parcelable
