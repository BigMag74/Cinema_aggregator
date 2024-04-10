package com.example.cinemaaggregator.searchScreen.domain

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import com.example.cinemaaggregator.searchScreen.domain.model.Field
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepository {

    fun search(text: String, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>>

    fun getCountries(): Flow<Pair<List<Field>?, ErrorStatus?>>

    fun getGenres(): Flow<Pair<List<Field>?, ErrorStatus?>>
}