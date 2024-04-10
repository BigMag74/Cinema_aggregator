package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.AddFieldToSearchHistoryUseCase
import javax.inject.Inject

class AddFieldToSearchHistoryUseCaseImpl @Inject constructor(
    private val repository: SearchScreenRepository
) : AddFieldToSearchHistoryUseCase {

    override fun execute(field: String) {
        repository.addFieldToSearchHistory(field)
    }
}