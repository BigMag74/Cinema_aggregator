package com.example.cinemaaggregator.searchScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface GetCountriesUseCase {

    fun execute(): Flow<Pair<List<String>?, ErrorStatus?>>
}