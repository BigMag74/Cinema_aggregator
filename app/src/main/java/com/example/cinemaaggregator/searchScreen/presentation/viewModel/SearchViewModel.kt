package com.example.cinemaaggregator.searchScreen.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.util.debounce
import com.example.cinemaaggregator.searchScreen.domain.model.Filters
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesUseCase
import com.example.cinemaaggregator.searchScreen.domain.model.MoviePartialModel
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetCountriesUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetGenresUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> = _state

    private val _countiesState = MutableLiveData<List<String>>()
    val countiesState: LiveData<List<String>> = _countiesState

    private val _genresState = MutableLiveData<List<String>>()
    val genresState: LiveData<List<String>> = _genresState

    private var page: Int = 1
    private var pages: Int = 1
    private var lastSearchRequestText = ""
    private val moviesList = mutableListOf<MoviePartialModel>()
    private var isNextPageLoading = false
    private var filters = Filters()

    init {
        getFiltersFields()
    }

    private fun setState(state: SearchState) {
        _state.value = state
    }

    fun setFilters(filters: Filters) {
        this.filters = filters
    }

    fun getFilters(): Filters {
        return filters
    }

    fun searchDebounced(changedText: String) {
        if (changedText != lastSearchRequestText) {
            movieSearchDebounce(changedText)
            lastSearchRequestText = changedText
        }
    }

    fun getFiltersFields() {
        if (genresState.value == null || countiesState.value == null) {
            viewModelScope.launch {
                getCountriesUseCase.execute().collect {
                    if (it.first != null && it.first!!.isNotEmpty())
                        _countiesState.value = it.first
                }
                getGenresUseCase.execute().collect {
                    if (it.first != null && it.first!!.isNotEmpty())
                        _genresState.value = it.first
                }
            }
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