package com.example.cinemaaggregator.movieScreen.presentation.viewModel

import com.example.cinemaaggregator.movieScreen.domain.model.MovieByIdResponse

sealed interface MovieScreenState {
    data object Loading : MovieScreenState

    data class Content(
        val movie: MovieByIdResponse
    ) : MovieScreenState

    data class Error(
        val errorMessageResId: Int
    ) : MovieScreenState
}