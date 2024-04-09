package com.example.cinemaaggregator.searchScreen.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinemaaggregator.databinding.FragmentSearchBinding
import com.example.cinemaaggregator.searchScreen.di.SearchScreenComponent
import com.example.cinemaaggregator.searchScreen.presentation.MovieRecyclerViewAdapter
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.SearchState
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        SearchScreenComponent.create()
    }
    private val viewModel by viewModels<SearchViewModel> { component.viewModelFactory() }

    private val adapter: MovieRecyclerViewAdapter by lazy {
        MovieRecyclerViewAdapter(requireContext()) {
            val direction = SearchFragmentDirections.actionSearchFragmentToMovieScreenFragment(it)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { renderState(it) }

        binding.searchScreenEditText.addTextChangedListener(searchTextWatcher)
        binding.searchScreenRecyclerView.adapter = adapter
    }

    private val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.movieSearchDebounce(s.toString())
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }

    private fun renderState(state: SearchState) {
        when (state) {
            is SearchState.Content -> {
                adapter.items = state.movies
                adapter.notifyDataSetChanged()
            }

            is SearchState.Empty -> {}
            is SearchState.Error -> {}
            is SearchState.Loading -> {}
            is SearchState.SearchHistory -> {}
        }
    }
}
