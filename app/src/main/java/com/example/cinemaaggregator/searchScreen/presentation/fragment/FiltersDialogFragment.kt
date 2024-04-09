package com.example.cinemaaggregator.searchScreen.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.databinding.DialogFiltersBinding
import com.example.cinemaaggregator.searchScreen.domain.model.Filters

class FiltersDialogFragment(
    private val countries: List<String>,
    private val genres: List<String>,
    private val onPositiveButtonClicked: (Filters) -> Unit
) : DialogFragment() {

    private var _binding: DialogFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        _binding = DialogFiltersBinding.inflate(layoutInflater)

        val countrySpinnerAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, countries) }
        countrySpinnerAdapter?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.countrySpinner.adapter = countrySpinnerAdapter

        val genreSpinnerAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, genres) }
        genreSpinnerAdapter?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.genreSpinner.adapter = genreSpinnerAdapter

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton("Применить") { _, _ ->
                val year = if (binding.yearET.text.isNotBlank()) binding.yearET.text.toString() else null
                val ageRating = if (binding.ageRatingET.text.isNotBlank()) binding.ageRatingET.text.toString() else null

                val country = if (binding.countrySpinner.selectedItem.toString() == getString(
                        R.string.not_selected
                    )
                ) null else binding.countrySpinner.selectedItem.toString()

                val genre = if (binding.genreSpinner.selectedItem.toString() == getString(
                        R.string.not_selected
                    )
                ) null else binding.genreSpinner.selectedItem.toString()

                val newFilters = Filters(year, country, ageRating, genre)
                onPositiveButtonClicked(newFilters)
            }
            .setNegativeButton("Отмена") { _, _ ->
                dialog?.cancel()
            }
            .create()
    }
}