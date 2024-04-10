package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetGenresUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGenresUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : GetGenresUseCase {

    override fun execute(): Flow<Pair<List<String>?, ErrorStatus?>> {
        val res = repository.getGenres()
        return res.map { pair ->
            Pair(pair.first?.map { field -> field.name }, pair.second)
        }
    }
}