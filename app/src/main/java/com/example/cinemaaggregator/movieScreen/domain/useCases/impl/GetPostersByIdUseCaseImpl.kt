package com.example.cinemaaggregator.movieScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetPostersByIdUseCase
import com.example.cinemaaggregator.searchScreen.domain.model.Poster
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostersByIdUseCaseImpl @Inject constructor(
    private val repository: MovieScreenRepository
): GetPostersByIdUseCase {

    override fun execute(id: Int): Flow<Pair<List<Poster>?, ErrorStatus?>> {
        return repository.getPostersById(id)
    }
}