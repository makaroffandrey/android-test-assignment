package com.example.shacklehotelbuddy.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shacklehotelbuddy.common.SearchParams
import com.example.shacklehotelbuddy.theme.ShackleHotelBuddyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.shacklehotelbuddy.strings.R as stringR

@Composable
internal fun RecentSearches(state: RecentSearchesState) {
    Column {
        Text(
            text = stringResource(stringR.string.search_screen_recent_searches),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 48.dp)
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.width(40.dp),
                    painter = painterResource(R.drawable.manage_history),
                    contentDescription = stringResource(stringR.string.search_screen_recent_searches)
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(16.dp))
                when (state) {
                    RecentSearchesState.Loading -> CircularProgressIndicator()
                    is RecentSearchesState.Loaded -> LoadedState(state.searches)
                }
            }
        }
    }
}

@Composable
private fun LoadedState(previousSearches: ImmutableList<SearchParams>) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd / MM / yyyy") }
    if (previousSearches.isNotEmpty()) {
        val search = previousSearches.first()
        val checkIn = dateFormatter.format(search.checkInDate)
        val checkOut = dateFormatter.format(search.checkOutDate)
        Row {
            Text(
                text =
                stringResource(
                    id = stringR.string.search_screen_recent_date_range,
                    checkIn,
                    checkOut
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(16.dp))
            val adults = pluralStringResource(
                id = stringR.plurals.search_screen_number_of_adults,
                count = search.adultsNumber,
                search.adultsNumber,
            )
            val children =
                pluralStringResource(
                    id = stringR.plurals.search_screen_number_of_children,
                    count = search.childrenNumber,
                    search.childrenNumber
                )
            Text(
                text = stringResource(
                    id = stringR.string.search_screen_recent_adults_children,
                    adults,
                    children
                ),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    } else {
        Text(text = stringResource(stringR.string.search_screen_no_recent_searches))
    }
}

@Preview
@Composable
private fun RecentSearchesPreview() {
    ShackleHotelBuddyTheme {
        RecentSearches(
            RecentSearchesState.Loaded(
                persistentListOf(
                    SearchParams(
                        checkInDate = LocalDate.of(2023, 1, 1),
                        checkOutDate = LocalDate.of(2023, 11, 23),
                        adultsNumber = 1,
                        childrenNumber = 2,
                    )
                )
            )
        )
    }
}
