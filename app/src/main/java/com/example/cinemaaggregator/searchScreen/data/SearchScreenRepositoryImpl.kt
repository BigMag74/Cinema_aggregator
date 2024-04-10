package com.example.cinemaaggregator.searchScreen.data

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.common.network.NetworkClient
import com.example.cinemaaggregator.searchScreen.data.dataSource.SearchHistoryDataSource
import com.example.cinemaaggregator.searchScreen.data.network.FieldRequest
import com.example.cinemaaggregator.searchScreen.data.network.FieldResponse
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import com.example.cinemaaggregator.searchScreen.data.network.MoviesSearchResponse
import com.example.cinemaaggregator.searchScreen.data.network.SearchByNameRequest
import com.example.cinemaaggregator.searchScreen.data.network.SearchWithFiltersRequest
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.model.Field
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchScreenRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val searchHistoryDataSource: SearchHistoryDataSource,
) : SearchScreenRepository {

    override fun search(text: String, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>> = flow {
        val options: HashMap<String, String> = hashMapOf(
            "page" to page.toString(),
            "query" to text
        )

        val response = networkClient.doRequest(SearchByNameRequest(options))

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

    override fun searchWithFilters(
        options: HashMap<String, String>,
    ): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>> = flow {
        val response = networkClient.doRequest(SearchWithFiltersRequest(options))

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

    override fun getCountries(): Flow<Pair<List<Field>?, ErrorStatus?>> = flow {
        val response = networkClient.doRequest(FieldRequest("countries.name"))
        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                emit(Pair((response as FieldResponse).names, null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }
    }

    override fun getGenres(): Flow<Pair<List<Field>?, ErrorStatus?>> = flow {
        val response = networkClient.doRequest(FieldRequest("genres.name"))
        when (response.resultCode) {
            -1 -> {
                emit(Pair(null, ErrorStatus.NO_CONNECTION))
            }

            200 -> {
                emit(Pair((response as FieldResponse).names, null))
            }

            else -> {
                emit(Pair(null, ErrorStatus.ERROR_OCCURRED))
            }
        }
    }

    override fun getSearchHistory(): ArrayList<String> {
        return searchHistoryDataSource.getSearchHistory()
    }

    override fun addFieldToSearchHistory(field: String) {
        searchHistoryDataSource.addFieldToSearchHistory(field)
    }
}