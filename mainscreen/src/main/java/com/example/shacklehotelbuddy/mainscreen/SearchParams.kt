package com.example.shacklehotelbuddy.mainscreen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchParams(
    val adultsNumber: Int
): Parcelable
