package com.example.cinemaaggregator.searchScreen.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.util.debounce
import com.example.cinemaaggregator.searchScreen.domain.model.Filters
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesByNameUseCase
import com.example.cinemaaggregator.searchScreen.domain.model.MoviePartialModel
import com.example.cinemaaggregator.searchScreen.domain.useCases.AddFieldToSearchHistoryUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetCountriesUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetGenresUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetSearchHistoryUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesByFiltersUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase,
    private val searchMoviesByFiltersUseCase: SearchMoviesByFiltersUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val addFieldToSearchHistoryUseCase: AddFieldToSearchHistoryUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> = _state

    private val _countiesState = MutableLiveData<List<String>>()
    val countiesState: LiveData<List<String>> = _countiesState

    private val _genresState = MutableLiveData<List<String>>()
    val genresState: LiveData<List<String>> = _genresState

    private val _filtersState = MutableLiveData<FiltersState>(FiltersState.Unavailable)
    val filtersState: LiveData<FiltersState> = _filtersState

    private val _searchHistoryState = MutableLiveData<List<String>>()
    val searchHistoryState: LiveData<List<String>> = _searchHistoryState

    private var page: Int = 1
    private var pages: Int = 1
    private var lastSearchRequestText = ""
    private val moviesList = mutableListOf<MoviePartialModel>()
    private var isNextPageLoading = false
    private var filters = Filters()

    private var isFiltersRequest = true

    init {
        getSearchHistory()
        getFiltersFields()
        searchByFiltersNewRequest()
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

    fun disableFiltersIcon() {
        _filtersState.value = FiltersState.Unavailable
    }

    fun enableFiltersIcon() {
        if (filters == Filters())
            _filtersState.value = FiltersState.Empty
        else
            _filtersState.value = FiltersState.Content
    }

    fun getSearchHistory() {
        _searchHistoryState.value = getSearchHistoryUseCase.execute()
    }

    private fun getFiltersFields() {
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
                if (_genresState.value != null && _countiesState.value != null)
                    _filtersState.value = FiltersState.Empty
            }
        }
    }

    fun searchByNameDebounced(changedText: String) {
        if (changedText != lastSearchRequestText) {
            movieSearchDebounce(changedText)
            lastSearchRequestText = changedText
        }
    }

    private val movieSearchDebounce = debounce<String>(DELAY_MILLIS, viewModelScope, true) {
        page = 1
        searchByNameNewRequest(it, page)
    }

    private fun searchByNameNewRequest(text: String, page: Int) {
        if (text.isBlank()) {
            return
        }
        addFieldToSearchHistoryUseCase.execute(text)
        setState(SearchState.Loading)
        viewModelScope.launch {
            searchMoviesByNameUseCase.execute(text, page).collect {
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
                            isFiltersRequest = false
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
                    if (isFiltersRequest) {
                        searchMoviesByFiltersUseCase.execute(filters, page + 1).collect {
                            if (it.first != null) {
                                page++
                                moviesList.addAll(it.first!!.movies)
                                setState(SearchState.Content(moviesList))
                            }
                        }
                    } else {
                        searchMoviesByNameUseCase.execute(lastSearchRequestText, page + 1).collect {
                            if (it.first != null) {
                                page++
                                moviesList.addAll(it.first!!.movies)
                                setState(SearchState.Content(moviesList))
                            }
                        }
                    }
                }
                resultDeferred.await()
                isNextPageLoading = false
            }
        }
    }

    fun searchByFiltersNewRequest() {
        setState(SearchState.Loading)
        if (filters == Filters())
            _filtersState.value = FiltersState.Empty
        else
            _filtersState.value = FiltersState.Content
        page = 1
        viewModelScope.launch {
            searchMoviesByFiltersUseCase.execute(filters, page).collect {
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
                            isFiltersRequest = true
                            setState(SearchState.Content(moviesList))
                        }
                    }
                }
            }
        }
    }

    companion object {

        const val DELAY_MILLIS = 1000L
    }
}