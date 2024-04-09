package com.example.cinemaaggregator.searchScreen.domain

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepository {

    fun search(text: String, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>>
}