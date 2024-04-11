package com.example.cinemaaggregator.movieScreen.domain.model

import com.example.cinemaaggregator.common.network.Response
import com.example.cinemaaggregator.searchScreen.domain.model.Country
import com.example.cinemaaggregator.searchScreen.domain.model.Poster

data class MovieByIdResponse(
    val id: Int,
    val name: String?,
    val year: Int?,
    val description: String?,
    val movieLength: Int?,
    val ageRating: Int?,
    val countries: List<Country>?,
    val poster: Poster?,
    val genres: List<Genre>?,
    val rating: Rating?,

) : Response()