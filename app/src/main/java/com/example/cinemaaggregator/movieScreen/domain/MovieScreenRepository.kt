package com.example.cinemaaggregator.movieScreen.domain

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdResponse
import com.example.cinemaaggregator.movieScreen.data.network.ReviewsResponse
import com.example.cinemaaggregator.movieScreen.data.network.SeasonsAndEpisodesResponse
import com.example.cinemaaggregator.searchScreen.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface MovieScreenRepository {

    suspend fun getMovieById(id: Int): Pair<MovieByIdResponse?, ErrorStatus?>

    fun getPostersById(id: Int): Flow<Pair<List<Poster>?, ErrorStatus?>>

    fun getReviewsById(id: Int): Flow<Pair<ReviewsResponse?, ErrorStatus?>>

    fun getSeasonsAndEpisodesById(id: Int): Flow<Pair<SeasonsAndEpisodesResponse?, ErrorStatus?>>
}