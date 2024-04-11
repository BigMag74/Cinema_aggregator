package com.example.cinemaaggregator.common.network

import com.example.cinemaaggregator.movieScreen.data.network.PostersResponse
import com.example.cinemaaggregator.movieScreen.data.network.MovieByIdResponse
import com.example.cinemaaggregator.movieScreen.data.network.ReviewsResponse
import com.example.cinemaaggregator.movieScreen.data.network.SeasonsAndEpisodesResponse
import com.example.cinemaaggregator.searchScreen.data.network.MoviesSearchResponse
import com.example.cinemaaggregator.searchScreen.domain.model.Field
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("v1.4/movie/{movieId}")
    suspend fun getMovieById(@Path("movieId") movieId: Int): MovieByIdResponse

    @GET("v1.4/image")
    suspend fun getPostersById(
        @Query("movieId") movieId: Int
    ): PostersResponse

    @GET("v1.4/review")
    suspend fun getReviewsById(
        @Query("movieId") movieId: Int
    ): ReviewsResponse

    @GET("v1.4/season")
    suspend fun getSeasonsAndEpisodesById(
        @Query("movieId") movieId: Int
    ): SeasonsAndEpisodesResponse
}