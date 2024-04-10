package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetSearchHistoryUseCase
import javax.inject.Inject

class GetSearchHistoryUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : GetSearchHistoryUseCase {

    override fun execute(): List<String> {
        return repository.getSearchHistory().reversed()
    }
}