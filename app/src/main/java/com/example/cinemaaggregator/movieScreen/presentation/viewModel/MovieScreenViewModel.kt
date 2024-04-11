package com.example.cinemaaggregator.movieScreen.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetMovieByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieScreenViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<MovieScreenState>()
    val state: LiveData<MovieScreenState> = _state

    fun getMovieById(id: Int) {
        setState(MovieScreenState.Loading)
        viewModelScope.launch {
            val res = getMovieByIdUseCase.execute(id)
            when (res.second) {
                ErrorStatus.NO_CONNECTION -> setState(MovieScreenState.Error(R.string.check_internet_connection))
                ErrorStatus.ERROR_OCCURRED -> setState(MovieScreenState.Error(R.string.server_error))
                null -> {
                    if (res.first != null)
                        setState(MovieScreenState.Content(res.first!!))
                }
            }
        }
    }

    private fun setState(state: MovieScreenState) {
        _state.value = state
    }
}