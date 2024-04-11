package com.example.cinemaaggregator.searchScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.domain.model.MoviesAndPageCount
import com.example.cinemaaggregator.searchScreen.domain.model.Filters
import kotlinx.coroutines.flow.Flow

interface SearchMoviesByFiltersUseCase {

    fun execute(filters: Filters, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>>
}