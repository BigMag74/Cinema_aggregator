package com.example.cinemaaggregator.searchScreen.domain.useCases

interface GetSearchHistoryUseCase {

    fun execute(): List<String>
}