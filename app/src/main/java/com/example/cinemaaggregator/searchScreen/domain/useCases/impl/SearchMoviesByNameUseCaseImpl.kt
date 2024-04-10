package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesByNameUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesByNameUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : SearchMoviesByNameUseCase {

    override fun execute(text: String, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>> {
        return repository.search(text, page)
    }
}