package com.example.cinemaaggregator.movieScreen.data.network

import com.example.cinemaaggregator.common.network.Response
import com.example.cinemaaggregator.movieScreen.domain.model.Review

class ReviewsResponse(
    val docs: List<Review>,
    val pages: Int,
) : Response()