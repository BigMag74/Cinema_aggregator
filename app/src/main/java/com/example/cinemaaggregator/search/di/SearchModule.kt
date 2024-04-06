package com.example.cinemaaggregator.search.di

import androidx.lifecycle.ViewModel
import com.example.cinemaaggregator.common.di.ViewModelKey
import com.example.cinemaaggregator.search.presentation.viewModel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchModule {

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun provideViewModel(impl: SearchViewModel): ViewModel
}