package com.example.cinemaaggregator.common.di

import com.example.cinemaaggregator.search.di.SearchModule
import dagger.Component

@Component(modules = [SearchModule::class])
interface AppComponent {

    fun viewModelsFactory(): ViewModelFactory
}