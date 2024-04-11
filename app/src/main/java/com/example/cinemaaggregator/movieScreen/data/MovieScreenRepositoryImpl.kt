package com.example.cinemaaggregator.movieScreen.data

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.network.NetworkClient
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdRequest
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.domain.model.MovieByIdResponse
import javax.inject.Inject

class MovieScreenRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : MovieScreenRepository {

    override suspend fun getMovieById(id: Int): Pair<MovieByIdResponse?, ErrorStatus?> {
        val response = networkClient.doRequest(MovieByIdRequest(id))
        return when (response.resultCode) {
            -1 -> {
                Pair(null, ErrorStatus.NO_CONNECTION)
            }

            200 -> {
                Pair(response as MovieByIdResponse, null)
            }

            else -> {
                Pair(null, ErrorStatus.ERROR_OCCURRED)
            }
        }
    }
}