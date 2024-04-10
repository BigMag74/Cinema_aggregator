package com.example.cinemaaggregator.searchScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface GetGenresUseCase {

    fun execute(): Flow<Pair<List<String>?, ErrorStatus?>>
}