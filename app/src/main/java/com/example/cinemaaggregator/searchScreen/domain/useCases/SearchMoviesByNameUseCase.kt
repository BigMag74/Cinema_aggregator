package com.example.cinemaaggregator.searchScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import kotlinx.coroutines.flow.Flow

interface SearchMoviesByNameUseCase {

    fun execute(text: String, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>>
}