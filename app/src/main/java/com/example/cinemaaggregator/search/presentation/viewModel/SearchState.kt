package com.example.cinemaaggregator.search.presentation.viewModel

import com.example.cinemaaggregator.search.presentation.model.FilmPartial

sealed interface SearchState {
    object Loading : SearchState

    data class Content(
        val tracks: List<FilmPartial>
    ) : SearchState

    data class Error(
        val errorMessageResId: Int
    ) : SearchState

    data class Empty(
        val emptyMessageResId: Int
    ) : SearchState

    data class SearchHistory(
        val tracks: List<FilmPartial>?
    ) : SearchState
}