package com.example.cinemaaggregator.common.network

import com.example.cinemaaggregator.searchScreen.data.network.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface KinopoiskApiService {

    @GET("movie/search")
    suspend fun searchByName(
        @QueryMap options: Map<String, String>
    ): MoviesSearchResponse

    @GET("movie")
    suspend fun searchWithFilters(
        @QueryMap options: Map<String, String>
    ): MoviesSearchResponse
}