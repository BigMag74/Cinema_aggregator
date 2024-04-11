package com.example.cinemaaggregator.movieScreen.domain

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.domain.model.MovieByIdResponse

interface MovieScreenRepository {

    suspend fun getMovieById(id:Int): Pair<MovieByIdResponse?, ErrorStatus?>
}