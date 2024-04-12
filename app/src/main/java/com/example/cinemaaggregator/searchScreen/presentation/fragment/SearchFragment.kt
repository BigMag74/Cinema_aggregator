package com.example.cinemaaggregator.searchScreen.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.databinding.FragmentSearchBinding
import com.example.cinemaaggregator.searchScreen.di.SearchScreenComponent
import com.example.cinemaaggregator.searchScreen.presentation.MovieRecyclerViewAdapter
import com.example.cinemaaggregator.searchScreen.presentation.SearchHistoryAdapter
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.FiltersState
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.SearchState
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val component by lazy { SearchScreenComponent.create() }
    private val viewModel by viewModels<SearchViewModel> { component.viewModelFactory() }

    private val countries: MutableList<String> by lazy { mutableListOf(getString(R.string.not_selected)) }
    private val genres: MutableList<String> by lazy { mutableListOf(getString(R.string.not_selected)) }

    private val adapterMovies: MovieRecyclerViewAdapter by lazy {
        MovieRecyclerViewAdapter(requireContext()) {
            val direction = SearchFragmentDirections.actionSearchFragmentToMovieScreenFragment(it)
            findNavController().navigate(direction)
        }
    }
    private val adapterSearchHistory: SearchHistoryAdapter by lazy {
        SearchHistoryAdapter() {
            binding.searchScreenEditText.setText(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { renderState(it) }
        viewModel.filtersState.observe(viewLifecycleOwner) { renderFiltersState(it) }
        viewModel.searchHistoryState.observe(viewLifecycleOwner) {
            adapterSearchHistory.items = it
            adapterSearchHistory.notifyDataSetChanged()
        }
        viewModel.countiesState.observe(viewLifecycleOwner) {
            countries.addAll(it)
        }
        viewModel.genresState.observe(viewLifecycleOwner) {
            genres.addAll(it)
        }

        binding.searchScreenEditText.addTextChangedListener(searchTextWatcher)
        binding.searchScreenRecyclerView.adapter = adapterMovies
        binding.searchHistoryRV.adapter = adapterSearchHistory
        binding.searchScreenRecyclerView.addOnScrollListener(onScrollListener)
        binding.searchToolbar.setOnMenuItemClickListener(menuItemClickListener)
        binding.searchFormButton.setOnClickListener {
            searchByNameDebounced(
                binding.searchScreenEditText.text.toString()
            )
        }
        binding.searchScreenEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.searchScreenEditText.text.isNotEmpty()) {
                searchByNameDebounced(binding.searchScreenEditText.text.toString())
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchScreenEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    private fun searchByNameDebounced(text: String) {
        viewModel.searchByNameDebounced(text)
        binding.searchHistoryRV.visibility = View.GONE
    }

    private val onScrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val pos = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val itemsCount = adapterMovies.itemCount
                if ((itemsCount > 0) && (pos >= itemsCount - 1)) {
                    viewModel.searchSameRequest()
                }
            }
        }
    }

    private val menuItemClickListener = { it: MenuItem ->
        when (it.itemId) {
            R.id.searchScreenToolbarFilterMenu -> {
                val dialogFragment = FiltersDialogFragment(
                    countries,
                    genres,
                    viewModel.getFilters(),
                ) { filters ->
                    viewModel.setFilters(filters)
                    viewModel.searchByFiltersNewRequest()
                }
                dialogFragment.show(childFragmentManager, "filters_dialog")
                true
            }

            else -> {
                true
            }
        }
    }

    private val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchByNameDebounced(s.toString())
            viewModel.getSearchHistory()
            if (!s.isNullOrEmpty()) {
                viewModel.disableFiltersIcon()
                binding.searchHistoryRV.visibility = View.VISIBLE
            } else {
                viewModel.enableFiltersIcon()
                binding.searchHistoryRV.visibility = View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }

    private fun renderFiltersState(state: FiltersState) {
        when (state) {
            is FiltersState.Content -> {
                setMenuFilterIcon(R.drawable.ic_filters_selected)
                binding.searchToolbar.menu.findItem(R.id.searchScreenToolbarFilterMenu).isEnabled = true
            }

            is FiltersState.Empty -> {
                setMenuFilterIcon(R.drawable.ic_filters_unselected)
                binding.searchToolbar.menu.findItem(R.id.searchScreenToolbarFilterMenu).isEnabled = true
            }

            is FiltersState.Unavailable -> {
                setMenuFilterIcon(R.drawable.ic_filters_unavailable)
                binding.searchToolbar.menu.findItem(R.id.searchScreenToolbarFilterMenu).isEnabled = false
            }
        }
    }

    private fun setMenuFilterIcon(drawableInt: Int) {
        binding.searchToolbar.menu.findItem(R.id.searchScreenToolbarFilterMenu).icon =
            AppCompatResources.getDrawable(requireContext(), drawableInt)
    }

    private fun renderState(state: SearchState) {
        when (state) {
            is SearchState.Content -> {
                showContent(state)
            }

            is SearchState.Empty -> {
                showEmpty()
            }

            is SearchState.Error -> {
                showError(state)
            }

            is SearchState.Loading -> {
                binding.searchScreenEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
                showLoading()
            }
        }
    }

    private fun showContent(state: SearchState.Content) {
        binding.nothingFoundPlaceholderTV.visibility = View.GONE
        binding.nothingFoundPlaceholderIV.visibility = View.GONE
        binding.serverErrorPlaceholderIV.visibility = View.GONE
        binding.serverErrorPlaceholderTV.visibility = View.GONE
        binding.searchScreenMainProgressBar.visibility = View.GONE
        binding.internetIssuesPlaceholderIV.visibility = View.GONE
        binding.internetIssuesPlaceholderTV.visibility = View.GONE
        binding.searchScreenRecyclerView.visibility = View.VISIBLE
        adapterMovies.items = state.movies
        adapterMovies.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.serverErrorPlaceholderIV.visibility = View.GONE
        binding.serverErrorPlaceholderTV.visibility = View.GONE
        binding.searchScreenMainProgressBar.visibility = View.GONE
        binding.searchScreenRecyclerView.visibility = View.GONE
        binding.internetIssuesPlaceholderIV.visibility = View.GONE
        binding.internetIssuesPlaceholderTV.visibility = View.GONE
        binding.nothingFoundPlaceholderTV.visibility = View.VISIBLE
        binding.nothingFoundPlaceholderIV.visibility = View.VISIBLE
    }

    private fun showError(state: SearchState.Error) {
        binding.searchScreenMainProgressBar.visibility = View.GONE
        binding.searchScreenRecyclerView.visibility = View.GONE
        binding.nothingFoundPlaceholderTV.visibility = View.GONE
        binding.nothingFoundPlaceholderIV.visibility = View.GONE
        if (state.errorMessageResId == R.string.server_error) {
            binding.serverErrorPlaceholderIV.visibility = View.VISIBLE
            binding.serverErrorPlaceholderTV.visibility = View.VISIBLE
        }
        if (state.errorMessageResId == R.string.check_internet_connection) {
            binding.internetIssuesPlaceholderIV.visibility = View.VISIBLE
            binding.internetIssuesPlaceholderTV.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.searchScreenRecyclerView.visibility = View.GONE
        binding.nothingFoundPlaceholderTV.visibility = View.GONE
        binding.nothingFoundPlaceholderIV.visibility = View.GONE
        binding.serverErrorPlaceholderIV.visibility = View.GONE
        binding.serverErrorPlaceholderTV.visibility = View.GONE
        binding.internetIssuesPlaceholderIV.visibility = View.GONE
        binding.internetIssuesPlaceholderTV.visibility = View.GONE
        binding.searchScreenMainProgressBar.visibility = View.VISIBLE
    }
}
