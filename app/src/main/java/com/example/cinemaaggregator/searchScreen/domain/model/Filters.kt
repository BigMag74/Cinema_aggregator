package com.example.cinemaaggregator.searchScreen.domain.model

data class Filters(
    val year: String? = null,
    val country: String? = null,
    val ageRating: String? = null,
    val genre: String? = null,
    val ratingKP: String? = null,
)