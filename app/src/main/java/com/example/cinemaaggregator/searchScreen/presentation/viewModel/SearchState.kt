package com.example.cinemaaggregator.searchScreen.presentation.viewModel

import com.example.cinemaaggregator.searchScreen.domain.model.MoviePartialModel

sealed interface SearchState {
    data object Loading : SearchState

    data class Content(
        val movies: List<MoviePartialModel>
    ) : SearchState

    data class Error(
        val errorMessageResId: Int
    ) : SearchState

    data object Empty : SearchState

}