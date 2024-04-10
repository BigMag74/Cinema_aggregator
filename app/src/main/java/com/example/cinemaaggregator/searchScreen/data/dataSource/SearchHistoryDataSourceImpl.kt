package com.example.cinemaaggregator.searchScreen.data.dataSource

import android.content.SharedPreferences
import javax.inject.Inject

class SearchHistoryDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SearchHistoryDataSource {

    override fun getSearchHistory(): ArrayList<String> {
        val size = sharedPreferences.getInt(ARRAY_LIST_SIZE, -1)
        val res = arrayListOf<String>()
        for (i in 0 until size) {
            res.add((sharedPreferences.getString(ELEMENT_AT_ + i, null)!!))
        }
        return res
    }

    override fun addFieldToSearchHistory(field: String) {
        val fields = getSearchHistory()
        if (fields.size < 20) {
            fields.add(field)
            fields[0]
        } else {
            for (i in 0 until fields.size - 1) {
                fields[i] = fields[i + 1]
            }
            fields[fields.size - 1] = field
        }
        val editSP = sharedPreferences.edit()
        editSP.putInt(ARRAY_LIST_SIZE, fields.size)
        for (i in 0 until fields.size) {
            editSP.putString(ELEMENT_AT_ + i, fields[i])
        }
        editSP.apply()
    }

    companion object {

        const val ARRAY_LIST_SIZE = "array_list_size"
        const val ELEMENT_AT_ = "element_at_"
    }
}