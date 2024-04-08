package com.example.cinemaaggregator.search.data.network

import com.example.cinemaaggregator.common.network.Response
import com.example.cinemaaggregator.search.presentation.model.MoviePartialModel

class MoviesSearchResponse(
    val docs: List<MoviePartialModel>,
    val page: Int,
    val pages: Int,
) : Response() {
}