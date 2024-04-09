package com.example.cinemaaggregator.searchScreen.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.cinemaaggregator.databinding.DialogFiltersBinding

class FiltersDialogFragment(
    private val countries: List<String>,
    private val genres: List<String>,
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
            .setPositiveButton("Применить") { dialog, id ->

            }
            .setNegativeButton("Отмена") { dialog, id ->
                getDialog()?.cancel()
            }
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}