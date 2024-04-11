package com.example.cinemaaggregator.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdRequest
import com.example.cinemaaggregator.searchScreen.data.network.FieldRequest
import com.example.cinemaaggregator.searchScreen.data.network.FieldResponse
import com.example.cinemaaggregator.searchScreen.data.network.SearchWithFiltersRequest
import com.example.cinemaaggregator.searchScreen.data.network.SearchByNameRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkClientImpl @Inject constructor(
    private val kinopoiskApiService: KinopoiskApiService,
    private val context: Context,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if (dto is SearchByNameRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = kinopoiskApiService.searchByName(dto.options)
                    response.apply { resultCode = 200 }
                } catch (e: Throwable) {
                    Response().apply { resultCode = 500 }
                }
            }
        }
        if (dto is SearchWithFiltersRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = kinopoiskApiService.searchWithFilters(dto.options)
                    response.apply { resultCode = 200 }
                } catch (e: Throwable) {
                    Response().apply { resultCode = 500 }
                }
            }
        }

        if (dto is FieldRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = FieldResponse(kinopoiskApiService.getFields(dto.field))
                    response.apply { resultCode = 200 }
                } catch (e: Throwable) {
                    Response().apply { resultCode = 500 }
                }
            }
        }

        if (dto is MovieByIdRequest) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = kinopoiskApiService.getMovieById(dto.movieId)
                    response.apply { resultCode = 200 }
                } catch (e: Throwable) {
                    Response().apply { resultCode = 500 }
                }
            }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}