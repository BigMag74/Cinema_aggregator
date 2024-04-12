package com.example.cinemaaggregator.movieScreen.domain.useCases

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.data.network.ReviewsResponse
import kotlinx.coroutines.flow.Flow

interface GetReviewsByIdUseCase {

    fun execute(id: Int, page: Int): Flow<Pair<ReviewsResponse?, ErrorStatus?>>
}