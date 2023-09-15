package com.example.shacklehotelbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shacklehotelbuddy.common.SearchParams
import com.example.shacklehotelbuddy.mainscreen.MainScreen
import com.example.shacklehotelbuddy.searchresults.SEARCH_RESULT_PARAM_NAME
import com.example.shacklehotelbuddy.searchresults.SearchResults
import com.example.shacklehotelbuddy.theme.ShackleHotelBuddyTheme
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val adapter =
        Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
            .adapter(SearchParams::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShackleHotelBuddyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(onSearchClicked = {
                            navController.navigate(
                                route = "searchResults/${adapter.toJson(it)}",
                            )
                        })
                    }
                    composable(
                        route = "searchResults/{$SEARCH_RESULT_PARAM_NAME}",
                        arguments = listOf(navArgument(name = SEARCH_RESULT_PARAM_NAME) {
                            type = SearchParamsNavType(adapter)
                        })
                    ) {
                        SearchResults()
                    }
                }
            }
        }
    }
}

class SearchParamsNavType(private val adapter: JsonAdapter<SearchParams>) :
    NavType<SearchParams>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): SearchParams? =
        BundleCompat.getParcelable(bundle, key, SearchParams::class.java)

    override fun parseValue(value: String): SearchParams =
        requireNotNull(adapter.fromJson(value)) {
            "Error parsing SearchParam from $value"
        }

    override fun put(bundle: Bundle, key: String, value: SearchParams) {
        bundle.putParcelable(key, value)
    }
}

private class LocalDateAdapter {
    @FromJson
    fun fromJson(json: String): LocalDate? = LocalDate.from(FORMATTER.parse(json))

    @ToJson
    fun toJson(value: LocalDate?): String = FORMATTER.format(value)

    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }
}
