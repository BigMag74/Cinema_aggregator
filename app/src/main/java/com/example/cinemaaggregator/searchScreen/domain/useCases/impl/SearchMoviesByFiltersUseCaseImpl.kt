package com.example.cinemaaggregator.searchScreen.domain.useCases.impl

import com.example.cinemaaggregator.common.network.ErrorStatus
import com.example.cinemaaggregator.searchScreen.data.network.MoviesAndPageCount
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.model.Filters
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesByFiltersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesByFiltersUseCaseImpl @Inject constructor(private val repository: SearchScreenRepository) : SearchMoviesByFiltersUseCase {

    override fun execute(filters: Filters, page: Int): Flow<Pair<MoviesAndPageCount?, ErrorStatus?>> {
        val options: HashMap<String, String> = hashMapOf()
        options["page"] = page.toString()
        if (filters.year != null) {
            options["year"] = filters.year
        }
        if (filters.country != null) {
            options["countries.name"] = filters.country
        }
        if (filters.ageRating != null) {
            options["ageRating"] = filters.ageRating
        }
        if (filters.genre != null) {
            options["genres.name"] = filters.genre
        }
        return repository.searchWithFilters(options)
    }
}