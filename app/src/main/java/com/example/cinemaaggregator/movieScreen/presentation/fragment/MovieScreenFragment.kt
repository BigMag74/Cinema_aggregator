package com.example.cinemaaggregator.movieScreen.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.common.util.getActors
import com.example.cinemaaggregator.common.util.getDirectors
import com.example.cinemaaggregator.common.util.getOperators
import com.example.cinemaaggregator.common.util.listToString
import com.example.cinemaaggregator.databinding.FragmentMovieBinding
import com.example.cinemaaggregator.movieScreen.di.MovieScreenComponent
import com.example.cinemaaggregator.movieScreen.presentation.ActorsAdapter
import com.example.cinemaaggregator.movieScreen.presentation.PosterAdapter
import com.example.cinemaaggregator.movieScreen.presentation.ReviewAdapter
import com.example.cinemaaggregator.movieScreen.presentation.viewModel.MovieScreenState
import com.example.cinemaaggregator.movieScreen.presentation.viewModel.MovieScreenViewModel

class MovieScreenFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val args: MovieScreenFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }

    private val component by lazy { MovieScreenComponent.create() }
    private val viewModel by viewModels<MovieScreenViewModel> { component.viewModelFactory() }

    private val posterAdapter by lazy { PosterAdapter(requireContext()) }
    private val actorsAdapter by lazy { ActorsAdapter(requireContext()) }
    private val reviewsAdapter by lazy { ReviewAdapter(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.postersState.observe(viewLifecycleOwner) {
            val stringList: List<String> = it.map { poster -> poster.url!! }
            posterAdapter.items.addAll(stringList)
            posterAdapter.notifyDataSetChanged()
        }
        viewModel.reviewsState.observe(viewLifecycleOwner) {
            reviewsAdapter.items = it
            reviewsAdapter.notifyDataSetChanged()
        }

        viewModel.getMovieById(movieId)
        viewModel.getPostersById(movieId)
        viewModel.getReviewsById(movieId)

        binding.postersRecyclerView.adapter = posterAdapter
        binding.actorsRV.adapter = actorsAdapter
        binding.reviewsRV.adapter = reviewsAdapter
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
        binding.countryRight.text = showContentOrPlaceHolder(state.movie.countries?.listToString())
        binding.genreRight.text = showContentOrPlaceHolder(state.movie.genres?.listToString())
        binding.directorRight.text = showContentOrPlaceHolder(state.movie.persons?.getDirectors())
        binding.operatorRight.text = showContentOrPlaceHolder(state.movie.persons?.getOperators())
        if (state.movie.ageRating == null)
            binding.ageRatingRight.text = getString(R.string.information_not_found)
        else
            binding.ageRatingRight.text = state.movie.ageRating.toString() + "+"
        if (state.movie.movieLength == null)
            binding.timeRight.text = getString(R.string.information_not_found)
        else
            state.movie.movieLength.toString() + " мин."
        if (state.movie.rating.toString() == "0.0")
            binding.ratingRight.text = getString(R.string.information_not_found)
        else
            state.movie.rating.toString()
        binding.description.text = showContentOrPlaceHolder(state.movie.description)

        state.movie.poster?.url?.let { posterAdapter.items.add(0, it) }
        posterAdapter.notifyDataSetChanged()

        if (state.movie.persons?.getActors() != null)
            actorsAdapter.items = state.movie.persons.getActors()
        actorsAdapter.notifyDataSetChanged()
    }

    private fun showError(state: MovieScreenState.Error) {
        // TODO
    }

    private fun showLoading() {
        // TODO
    }

    private fun showContentOrPlaceHolder(content: String?): String {
        if (content.isNullOrEmpty() || content == "null") {
            return getString(R.string.information_not_found)
        }
        return content
    }
}