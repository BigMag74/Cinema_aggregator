package com.example.cinemaaggregator.searchScreen.data.dataSource

interface SearchHistoryDataSource {

    fun getSearchHistory(): ArrayList<String>
    fun addFieldToSearchHistory(field: String)
}