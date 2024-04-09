package com.example.cinemaaggregator.searchScreen.data

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.network.NetworkClient
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import com.example.cinemaaggregator.searchScreen.data.network.MoviesSearchResponse
import com.example.cinemaaggregator.searchScreen.data.network.SearchRequest
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchScreenRepositoryImpl @Inject constructor(
    private val networkCLient: NetworkClient
) : SearchScreenRepository {

    override fun search(text: String, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>> = flow {
        val options: HashMap<String, String> = hashMapOf(
            "page" to page.toString(),
            "query" to text
        )

        val response = networkCLient.doRequest(
            SearchRequest(options)
        )

        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                val moviesAndPageCount = MoviesAndPageCount(
                    (response as MoviesSearchResponse).docs,
                    response.pages
                )
                emit(Pair(moviesAndPageCount, null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }

    }
}