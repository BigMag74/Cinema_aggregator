package com.example.cinemaaggregator.searchScreen.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemaaggregator.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter(
    private val onItemClickListener: (item: String) -> Unit = {}
) : Adapter<SearchHistoryViewHolder>() {

    var items = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchHistoryViewHolder(
            ItemSearchHistoryBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onItemClickListener(items[position]) }
    }
}

class SearchHistoryViewHolder(
    private val binding: ItemSearchHistoryBinding
) : ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.searchHistoryTV.text = item
    }
}