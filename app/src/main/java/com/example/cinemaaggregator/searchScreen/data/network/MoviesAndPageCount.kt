package com.example.cinemaaggregator.searchScreen.data.network

import com.example.cinemaaggregator.searchScreen.presentation.model.MoviePartialModel

data class MoviesAndPageCount(
    val movies: List<MoviePartialModel>,
    val pageCount: Int,
)
