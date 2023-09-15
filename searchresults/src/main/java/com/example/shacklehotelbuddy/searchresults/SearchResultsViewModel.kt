package com.example.shacklehotelbuddy.searchresults

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.shacklehotelbuddy.common.SearchParams
import com.example.shacklehotelbuddy.networking.PropertiesApi
import com.example.shacklehotelbuddy.networking.PropertiesApiProvider
import com.example.shacklehotelbuddy.networking.PropertyListRequest
import com.example.shacklehotelbuddy.networking.PropertyRoom
import com.example.shacklehotelbuddy.networking.RequestDate
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class SearchResultsViewModel(
    savedStateHandle: SavedStateHandle,
    private val api: PropertiesApi,
) : ViewModel() {

    private val searchParams: SearchParams =
        requireNotNull(savedStateHandle[SEARCH_RESULT_PARAM_NAME]) {
            "SearchResultsViewModel was created without $SEARCH_RESULT_PARAM_NAME"
        }

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State>
        get() = _state

    init {
        retry()
    }

    fun retry() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _state.value = State.Error(throwable.localizedMessage ?: "")
        }) {
            _state.value = State.Loading
            val response = api.getProperties(
                PropertyListRequest(
                    checkInDate = RequestDate(searchParams.checkInDate),
                    checkOutDate = RequestDate(searchParams.checkOutDate),
                    rooms = listOf(
                        PropertyRoom(adults = searchParams.adultsNumber)
                    )
                )
            )
            _state.value = State.Loaded(response.data.properties.map { it.name })
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as PropertiesApiProvider
                val savedStateHandle = extras.createSavedStateHandle()

                return SearchResultsViewModel(savedStateHandle, application.propertiesApi) as T
            }
        }
    }
}

internal interface State {
    data object Loading : State
    data class Loaded(val hotels: List<String>) : State
    data class Error(var message: String) : State
}

const val SEARCH_RESULT_PARAM_NAME = "searchParams"
