package com.example.cinemaaggregator.movieScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface GetPostersByIdUseCase {

    fun execute(id: Int): Flow<Pair<List<Poster>?, ErrorStatus?>>
}