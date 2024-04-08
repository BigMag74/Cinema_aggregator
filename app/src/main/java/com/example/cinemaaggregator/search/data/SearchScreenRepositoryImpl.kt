package com.example.cinemaaggregator.search.data

import android.util.Log
import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.network.NetworkClient
import com.example.cinemaaggregator.search.data.network.MoviesSearchResponse
import com.example.cinemaaggregator.search.data.network.SearchRequest
import com.example.cinemaaggregator.search.domain.SearchScreenRepository
import com.example.cinemaaggregator.search.presentation.model.MoviePartialModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchScreenRepositoryImpl @Inject constructor(
    private val networkCLient: NetworkClient
) : SearchScreenRepository {

    override fun search(text: String, page: Int): Flow<Pair<List<MoviePartialModel>?, ErrorStatus?>> = flow {
        val options: HashMap<String, String> = hashMapOf(
            "page" to page.toString(),
            "query" to text
        )

        val response = networkCLient.doRequest(
            SearchRequest(options)
        )
        Log.e("MyTag", response.resultCode.toString())

        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                emit(Pair((response as MoviesSearchResponse).docs, null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }

    }
}