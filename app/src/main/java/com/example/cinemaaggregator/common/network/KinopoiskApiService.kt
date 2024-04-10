package com.example.cinemaaggregator.common.network

import com.example.cinemaaggregator.searchScreen.data.network.MoviesSearchResponse
import com.example.cinemaaggregator.searchScreen.domain.model.Field
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KinopoiskApiService {

    @GET("v1.4/movie/search")
    suspend fun searchByName(
        @QueryMap options: Map<String, String>
    ): MoviesSearchResponse

    @GET("v1.4/movie")
    suspend fun searchWithFilters(
        @QueryMap options: Map<String, String>
    ): MoviesSearchResponse

    @GET("v1/movie/possible-values-by-field")
    suspend fun getFields(
        @Query("field") field: String
    ): List<Field>
}