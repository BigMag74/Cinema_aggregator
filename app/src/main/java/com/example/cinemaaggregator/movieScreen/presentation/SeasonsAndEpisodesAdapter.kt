package com.example.cinemaaggregator.movieScreen.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemaaggregator.databinding.ItemEpisodesBinding
import com.example.cinemaaggregator.movieScreen.domain.model.Season

class SeasonsAndEpisodesAdapter : Adapter<SeasonsAndEpisodesViewHolder>() {

    var items = listOf<Season>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsAndEpisodesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SeasonsAndEpisodesViewHolder(ItemEpisodesBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SeasonsAndEpisodesViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class SeasonsAndEpisodesViewHolder(
    private val binding: ItemEpisodesBinding
) : ViewHolder(binding.root) {

    fun bind(item: Season) {
        binding.seasonName.text = item.name
        var episodes = ""
        item.episodes?.forEach { episode ->
            episodes += "Эпизод ${episode.number}. ${episode.name}\n"
        }
        binding.episodes.text = episodes
    }
}