package com.example.cinemaaggregator.movieScreen.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.util.listToString
import com.example.cinemaaggregator.databinding.FragmentMovieBinding
import com.example.cinemaaggregator.movieScreen.di.MovieScreenComponent
import com.example.cinemaaggregator.movieScreen.presentation.viewModel.MovieScreenState
import com.example.cinemaaggregator.movieScreen.presentation.viewModel.MovieScreenViewModel

class MovieScreenFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val args: MovieScreenFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }

    private val component by lazy { MovieScreenComponent.create() }
    private val viewModel by viewModels<MovieScreenViewModel> { component.viewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.getMovieById(movieId)
    }

    private fun render(state: MovieScreenState) {
        when (state) {
            is MovieScreenState.Content -> {
                showContent(state)
            }

            is MovieScreenState.Error -> {
                showError(state)
            }

            is MovieScreenState.Loading -> {
                showLoading()
            }
        }
    }

    private fun showContent(state: MovieScreenState.Content) {
        binding.titleTV.text = showContentOrPlaceHolder(state.movie.name)
        binding.yearRight.text = showContentOrPlaceHolder(state.movie.year.toString())
        binding.countryRight.text = showContentOrPlaceHolder(state.movie.countries?.listToString().toString())
        binding.genreRight.text = showContentOrPlaceHolder(state.movie.genres?.listToString().toString())
//        binding.directorRight.text = showContentOrPlaceHolder(state.movie.director.toString())
//        binding.operatorRight.text = showContentOrPlaceHolder(state.movie.operator.toString())
        binding.ageRatingRight.text = showContentOrPlaceHolder(state.movie.ageRating.toString() + "+")
        binding.timeRight.text = showContentOrPlaceHolder(state.movie.movieLength.toString() + " мин.")
        binding.ratingRight.text = showContentOrPlaceHolder(state.movie.rating.toString())
        binding.description.text = showContentOrPlaceHolder(state.movie.description)
    }

    private fun showError(state: MovieScreenState.Error) {
    }

    private fun showLoading() {
    }

    private fun showContentOrPlaceHolder(content: String?): String {
        if (content.isNullOrEmpty()) {
            return getString(R.string.information_not_found)
        }
        return content
    }
}