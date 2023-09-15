package com.example.shacklehotelbuddy.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shacklehotelbuddy.common.SearchParams
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class RecentSearchesViewModel : ViewModel() {
    private val _state = MutableStateFlow<RecentSearchesState>(RecentSearchesState.Loading)
    val state: StateFlow<RecentSearchesState>
        get() = _state.map {
            when (it) {
                RecentSearchesState.Loading -> it
                is RecentSearchesState.Loaded -> RecentSearchesState.Loaded(
                    it.searches.subList(
                        0,
                        it.searches.size.coerceAtMost(1) // Designs are unclear if we are supposed to display more than one
                    )
                )
            }
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RecentSearchesState.Loading,
        )

    init {
        // imitate loading from somewhere
        viewModelScope.launch {
            delay(3000)
            _state.compareAndSet(
                RecentSearchesState.Loading,
                RecentSearchesState.Loaded(persistentListOf())
            )
        }
    }

    fun onSearch(search: SearchParams) {
        // TODO: persist recent searches
        _state.value =
            RecentSearchesState.Loaded(
                (listOf(search) + (((_state.value as? RecentSearchesState.Loaded)?.searches)
                    ?: emptyList())).toImmutableList()
            )
    }
}

internal sealed interface RecentSearchesState {
    data object Loading : RecentSearchesState
    data class Loaded(val searches: ImmutableList<SearchParams>) : RecentSearchesState
}
