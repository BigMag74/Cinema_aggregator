package com.example.cinemaaggregator.movieScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.data.network.SeasonsAndEpisodesResponse
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetSeasonsAndEpisodesByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeasonsAndEpisodesByIdUseCaseImpl @Inject constructor(
    private val repository: MovieScreenRepository
) : GetSeasonsAndEpisodesByIdUseCase {

    override fun execute(id: Int): Flow<Pair<SeasonsAndEpisodesResponse?, ErrorStatus?>> {
        return repository.getSeasonsAndEpisodesById(id)
    }
}