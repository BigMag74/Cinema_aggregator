package com.example.cinemaaggregator.searchScreen.domain

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.presentation.model.MoviePartialModel
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepository {

    fun search(text: String, page: Int): Flow<Pair<List<MoviePartialModel>?, ErrorStatus?>>
}