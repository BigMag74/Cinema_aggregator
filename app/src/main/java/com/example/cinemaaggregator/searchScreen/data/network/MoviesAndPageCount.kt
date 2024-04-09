package com.example.cinemaaggregator.searchScreen.data.network

import com.example.cinemaaggregator.searchScreen.domain.model.MoviePartialModel

data class MoviesAndPageCount(
    val movies: List<MoviePartialModel>,
    val pageCount: Int,
)
