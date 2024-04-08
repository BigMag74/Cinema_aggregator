package com.example.cinemaaggregator.search.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.search.domain.SearchScreenRepository
import com.example.cinemaaggregator.search.domain.useCases.SearchMoviesUseCase
import com.example.cinemaaggregator.search.presentation.model.MoviePartialModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : SearchMoviesUseCase {

    override fun execute(text: String, page: Int): Flow<Pair<List<MoviePartialModel>?, ErrorStatus?>> {
        return repository.search(text, page)
    }
}