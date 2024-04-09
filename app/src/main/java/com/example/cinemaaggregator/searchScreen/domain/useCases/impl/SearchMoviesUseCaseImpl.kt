package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesUseCase
import com.example.cinemaaggregator.searchScreen.presentation.model.MoviePartialModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : SearchMoviesUseCase {

    override fun execute(text: String, page: Int): Flow<Pair<List<MoviePartialModel>?, ErrorStatus?>> {
        return repository.search(text, page)
    }
}