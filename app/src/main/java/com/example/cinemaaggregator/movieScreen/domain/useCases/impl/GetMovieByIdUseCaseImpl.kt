package com.example.cinemaaggregator.movieScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdResponse
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetMovieByIdUseCase
import javax.inject.Inject

class GetMovieByIdUseCaseImpl @Inject constructor(
    private val repository: MovieScreenRepository,
) : GetMovieByIdUseCase {

    override suspend fun execute(id: Int): Pair<MovieByIdResponse?, ErrorStatus?> {
        return repository.getMovieById(id)
    }
}