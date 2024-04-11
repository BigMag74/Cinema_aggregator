package com.example.cinemaaggregator.searchScreen.domain.model

data class MoviesAndPageCount(
    val movies: List<MoviePartialModel>,
    val pageCount: Int,
)
