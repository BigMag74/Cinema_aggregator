package com.example.cinemaaggregator.search.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> = _state

    private fun setState(state: SearchState) {
        _state.value = state
    }
}