package com.example.cinemaaggregator.search.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cinemaaggregator.databinding.FragmentSearchBinding
import com.example.cinemaaggregator.search.di.SearchScreenComponent
import com.example.cinemaaggregator.search.presentation.viewModel.SearchState
import com.example.cinemaaggregator.search.presentation.viewModel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        SearchScreenComponent.create()
    }
    private val viewModel by viewModels<SearchViewModel> { component.viewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SearchState.Content -> Log.e("MyTag", "ViewModel Content")
                is SearchState.Empty -> Log.e("MyTag", "ViewModel Empty")
                is SearchState.Error -> Log.e("MyTag", "ViewModel Error")
                is SearchState.Loading -> {
                    Log.e("MyTag", "ViewModel Loading")
                }

                is SearchState.SearchHistory -> Log.e("MyTag", "ViewModel SearchHistory")
            }
        }
    }
}
