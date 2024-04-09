package com.example.cinemaaggregator.searchScreen.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.util.debounce
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesUseCase
import com.example.cinemaaggregator.searchScreen.domain.model.MoviePartialModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> = _state

    private var page: Int = 1
    private var pages: Int = 1
    private var lastSearchRequestText = ""
    private val moviesList = mutableListOf<MoviePartialModel>()
    private var isNextPageLoading = false

    private fun setState(state: SearchState) {
        _state.value = state
    }

    fun searchDebounced(changedText: String) {
        if (changedText != lastSearchRequestText) {
            movieSearchDebounce(changedText)
            lastSearchRequestText = changedText
        }
    }

    private val movieSearchDebounce = debounce<String>(DELAY_MILLIS, viewModelScope, true) {
        page = 1
        searchNewRequest(it, page)
    }

    private fun searchNewRequest(text: String, page: Int) {
        if (text.isBlank()) {
            return
        }
        setState(SearchState.Loading)
        viewModelScope.launch {
            searchMoviesUseCase.execute(text, page).collect {
                when (it.second) {
                    ErrorStatus.NO_CONNECTION -> {
                        setState(SearchState.Error(R.string.check_internet_connection))
                    }

                    ErrorStatus.ERROR_OCCURRED -> {
                        setState(SearchState.Error(R.string.server_error))
                    }

                    null -> {
                        if (it.first!!.movies.isEmpty()) {
                            setState(SearchState.Empty)
                        } else {
                            moviesList.clear()
                            moviesList.addAll(it.first!!.movies)
                            pages = it.first!!.pageCount
                            setState(SearchState.Content(moviesList))
                        }
                    }
                }
            }

        }
    }

    fun searchSameRequest() {
        if (page < pages && !isNextPageLoading) {
            isNextPageLoading = true
            viewModelScope.launch {
                val resultDeferred = async {
                    searchMoviesUseCase.execute(lastSearchRequestText, page + 1).collect {
                        if (it.first != null) {
                            page++
                            moviesList.addAll(it.first!!.movies)
                            setState(SearchState.Content(moviesList))
                        }
                    }
                }
                resultDeferred.await()
                isNextPageLoading = false
            }
        }
    }

    companion object {

        const val DELAY_MILLIS = 1000L
    }
}