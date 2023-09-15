package com.example.shacklehotelbuddy.searchresults

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchResults(
) {
    SearchResultsInternal()
}

@Composable
internal fun SearchResultsInternal(
    viewModel: SearchResultsViewModel = viewModel(factory = SearchResultsViewModel.Factory)
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    // TODO: Display the state
}
