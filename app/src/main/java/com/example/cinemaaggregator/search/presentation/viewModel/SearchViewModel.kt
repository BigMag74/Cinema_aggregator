package com.example.cinemaaggregator.search.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaaggregator.common.util.debounce
import com.example.cinemaaggregator.search.domain.useCases.SearchMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> = _state

    private var page: Int = 1

    init {
        setState(SearchState.Loading)
    }

    private fun setState(state: SearchState) {
        _state.value = state
    }

    val movieSearchDebounce = debounce<String>(1000, viewModelScope, true) {
        searchNewRequest(it, page)
    }

    private fun searchNewRequest(text: String, page: Int) {
        if (text.isBlank()) {
            return
        }
        setState(SearchState.Loading)
        viewModelScope.launch {
            searchMoviesUseCase.execute(text, page).collect {
                if (it.first != null) {
                    setState(SearchState.Content(it.first!!))
                }
            }

        }
    }
}