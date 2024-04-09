package com.example.cinemaaggregator.searchScreen.domain.model

data class Filters(
    val year: Int,
    val country: String,
    val ageRating: Int,
    val type: String,
)