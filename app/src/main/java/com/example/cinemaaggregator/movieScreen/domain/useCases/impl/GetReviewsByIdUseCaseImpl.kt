package com.example.cinemaaggregator.movieScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.data.network.ReviewsResponse
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetReviewsByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsByIdUseCaseImpl @Inject constructor(
    private val repository: MovieScreenRepository
) : GetReviewsByIdUseCase {

    override fun execute(id: Int, page: Int): Flow<Pair<ReviewsResponse?, ErrorStatus?>> {
        val options = hashMapOf(
            "movieId" to id.toString(),
            "page" to page.toString()
        )
        return repository.getReviewsById(options)
    }
}