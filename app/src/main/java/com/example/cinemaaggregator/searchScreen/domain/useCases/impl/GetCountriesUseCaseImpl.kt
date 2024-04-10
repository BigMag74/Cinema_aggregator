package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetCountriesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCountriesUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : GetCountriesUseCase {

    override fun execute(): Flow<Pair<List<String>?, ErrorStatus?>> {
        val res = repository.getCountries()
        return res.map { pair ->
            Pair(pair.first?.map { field -> field.name }, pair.second)
        }
    }
}