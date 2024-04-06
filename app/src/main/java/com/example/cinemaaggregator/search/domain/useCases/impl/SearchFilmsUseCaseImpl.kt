package com.example.cinemaaggregator.search.domain.useCases.impl

import com.example.cinemaaggregator.search.domain.SearchScreenRepository
import com.example.cinemaaggregator.search.domain.useCases.SearchFilmsUseCase
import javax.inject.Inject

class SearchFilmsUseCaseImpl @Inject constructor(val repository: SearchScreenRepository) : SearchFilmsUseCase {
}