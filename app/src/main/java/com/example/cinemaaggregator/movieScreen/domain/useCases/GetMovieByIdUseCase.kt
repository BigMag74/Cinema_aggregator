package com.example.cinemaaggregator.movieScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdResponse

interface GetMovieByIdUseCase {

    suspend fun execute(id:Int): Pair<MovieByIdResponse?, ErrorStatus?>
}