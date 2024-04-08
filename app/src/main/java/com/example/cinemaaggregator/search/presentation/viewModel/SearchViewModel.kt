package com.example.cinemaaggregator.search.presentation.viewModel

import android.util.Log
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
        searchNewRequest("spider man", page)
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
                it.first?.get(1)?.countries.let { it1 -> Log.e("MyTag", it1.toString()) }
            }

        }
        this.page++
    }
}