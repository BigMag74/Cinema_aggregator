package com.example.cinemaaggregator.search.presentation.model

data class MoviePartialModel(
    val id: Int,
    val name: String?,
    val year: Int?,
    val description: String?,
    val movieLength: Int?,
    val ageRating: Int?,
    val countries: List<Country>?,
    val poster: Poster?
)
