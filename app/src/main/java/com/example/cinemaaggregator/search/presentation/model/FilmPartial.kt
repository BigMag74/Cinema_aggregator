package com.example.cinemaaggregator.search.presentation.model

data class FilmPartial(
    val id: Int,
    val name: String?,
    val year: Int?,
    val shortDescription: String?,
    val movieLength: Int?,
)
