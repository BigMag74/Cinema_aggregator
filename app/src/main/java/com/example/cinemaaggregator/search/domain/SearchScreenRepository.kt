package com.example.cinemaaggregator.search.domain

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.search.presentation.model.MoviePartialModel
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepository {

    fun search(text: String, page: Int): Flow<Pair<List<MoviePartialModel>?, ErrorStatus?>>
}