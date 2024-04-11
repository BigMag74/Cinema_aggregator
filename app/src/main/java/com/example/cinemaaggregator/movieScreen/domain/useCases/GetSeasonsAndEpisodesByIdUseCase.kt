package com.example.cinemaaggregator.movieScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.data.network.SeasonsAndEpisodesResponse
import kotlinx.coroutines.flow.Flow

interface GetSeasonsAndEpisodesByIdUseCase {

    fun execute(id: Int): Flow<Pair<SeasonsAndEpisodesResponse?, ErrorStatus?>>
}