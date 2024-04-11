package com.example.cinemaaggregator.movieScreen.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.movieScreen.domain.model.Review
import com.example.cinemaaggregator.movieScreen.domain.model.Season
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetMovieByIdUseCase
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetPostersByIdUseCase
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetReviewsByIdUseCase
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetSeasonsAndEpisodesByIdUseCase
import com.example.cinemaaggregator.searchScreen.domain.model.Poster
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieScreenViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getPostersByIdUseCase: GetPostersByIdUseCase,
    private val getReviewsByIdUseCase: GetReviewsByIdUseCase,
    private val getSeasonsAndEpisodesByIdUseCase: GetSeasonsAndEpisodesByIdUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<MovieScreenState>()
    val state: LiveData<MovieScreenState> = _state

    private val _postersState = MutableLiveData<List<Poster>>()
    val postersState: LiveData<List<Poster>> = _postersState

    private val _reviewsState = MutableLiveData<List<Review>>()
    val reviewsState: LiveData<List<Review>> = _reviewsState

    private val _seasonsAndEpisodesState = MutableLiveData<List<Season>>()
    val seasonsAndEpisodesState: LiveData<List<Season>> = _seasonsAndEpisodesState

    private var reviewsPages = 0

    fun findMovieInformationById(id: Int){
        getMovieById(id)
        getPostersById(id)
        getReviewsById(id)
        getSeasonsAndEpisodesById(id)
    }

    private fun getMovieById(id: Int) {
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

    private fun getPostersById(id: Int) {
        viewModelScope.launch {
            getPostersByIdUseCase.execute(id).collect {
                if (it.first != null) {
                    _postersState.value = it.first
                }
            }
        }
    }

    private fun getReviewsById(id: Int) {
        viewModelScope.launch {
            getReviewsByIdUseCase.execute(id).collect {
                if (it.first != null) {
                    _reviewsState.value = it.first!!.docs
                    reviewsPages = it.first!!.pages
                }
            }
        }
    }

    private fun getSeasonsAndEpisodesById(id: Int) {
        viewModelScope.launch {
            getSeasonsAndEpisodesByIdUseCase.execute(id).collect {
                if (it.first != null) {
                    _seasonsAndEpisodesState.value = it.first!!.docs.reversed()
                }
            }
        }
    }

    private fun setState(state: MovieScreenState) {
        _state.value = state
    }
}