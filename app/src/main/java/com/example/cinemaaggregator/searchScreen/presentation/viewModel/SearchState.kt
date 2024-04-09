package com.example.cinemaaggregator.searchScreen.presentation.viewModel

import com.example.cinemaaggregator.searchScreen.presentation.model.MoviePartialModel

sealed interface SearchState {
    object Loading : SearchState

    data class Content(
        val movies: List<MoviePartialModel>
    ) : SearchState

    data class Error(
        val errorMessageResId: Int
    ) : SearchState

    data class Empty(
        val emptyMessageResId: Int
    ) : SearchState

    data class SearchHistory(
        val movies: List<MoviePartialModel>?
    ) : SearchState
}