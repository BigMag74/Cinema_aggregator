package com.example.cinemaaggregator.movieScreen.data

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.network.NetworkClient
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdRequest
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdResponse
import com.example.cinemaaggregator.movieScreen.data.network.PostersRequest
import com.example.cinemaaggregator.movieScreen.data.network.PostersResponse
import com.example.cinemaaggregator.movieScreen.data.network.ReviewsRequest
import com.example.cinemaaggregator.movieScreen.data.network.ReviewsResponse
import com.example.cinemaaggregator.movieScreen.data.network.SeasonsAndEpisodesRequest
import com.example.cinemaaggregator.movieScreen.data.network.SeasonsAndEpisodesResponse
import com.example.cinemaaggregator.searchScreen.domain.model.Poster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getPostersById(id: Int): Flow<Pair<List<Poster>?, ErrorStatus?>> = flow {
        val response = networkClient.doRequest(PostersRequest(id))
        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                emit(Pair((response as PostersResponse).docs, null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }
    }

    override fun getReviewsById(options: HashMap<String, String>): Flow<Pair<ReviewsResponse?, ErrorStatus?>> = flow {
        val response = networkClient.doRequest(ReviewsRequest(options))
        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                emit(Pair((response as ReviewsResponse), null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }
    }

    override fun getSeasonsAndEpisodesById(id: Int): Flow<Pair<SeasonsAndEpisodesResponse?, ErrorStatus?>> = flow {
        val response = networkClient.doRequest(SeasonsAndEpisodesRequest(id))
        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                emit(Pair((response as SeasonsAndEpisodesResponse), null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }
    }
}