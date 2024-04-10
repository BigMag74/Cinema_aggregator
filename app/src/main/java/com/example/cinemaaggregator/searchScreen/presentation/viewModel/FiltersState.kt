package com.example.cinemaaggregator.searchScreen.presentation.viewModel

sealed interface FiltersState {
    data object Empty : FiltersState
    data object Content : FiltersState
    data object Unavailable : FiltersState
}