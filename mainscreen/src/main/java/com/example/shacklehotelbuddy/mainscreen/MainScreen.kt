package com.example.shacklehotelbuddy.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shacklehotelbuddy.theme.ShackleHotelBuddyTheme
import com.example.shacklehotelbuddy.strings.R as stringR

@Composable
fun MainScreen(onSearchClicked: (SearchParams) -> Unit) {
    MainScreenInternal(onSearchClicked = onSearchClicked)
}

@Composable
internal fun MainScreenInternal(
    onSearchClicked: (SearchParams) -> Unit,
    recentSearchesViewModel: RecentSearchesViewModel = viewModel()
) {
    val state = rememberSearchParamSelectorState()
    val recentSearchesState = recentSearchesViewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillWidth
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(stringR.string.search_screen_title),
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            SearchParamSelector(
                state = state,
            )
            Spacer(modifier = Modifier.height(32.dp))
            RecentSearches(state = recentSearchesState.value)
        }
        val searchParams = state.toSearchParams()
        Button(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 40.dp, end = 16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            enabled = searchParams != null,
            onClick = {
                searchParams?.let {
                    recentSearchesViewModel.onSearch(it)
                    onSearchClicked(it)
                }
            }) {
            Text(
                modifier = Modifier.padding(vertical = 19.dp),
                text = stringResource(stringR.string.search_screen_search),
                fontSize = 18.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreviewLight() {
    ShackleHotelBuddyTheme(
        isDarkTheme = false
    ) {
        MainScreen(onSearchClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreviewDark() {
    ShackleHotelBuddyTheme(
        isDarkTheme = true
    ) {
        MainScreen(onSearchClicked = {})
    }
}
