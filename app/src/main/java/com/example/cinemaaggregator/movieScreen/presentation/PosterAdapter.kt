package com.example.cinemaaggregator.movieScreen.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.databinding.ItemPosterBinding
import com.example.cinemaaggregator.searchScreen.domain.model.Poster

class PosterAdapter(
    private val context: Context,
) : ListAdapter<Poster, PosterViewHolder>(DiffCallback()) {

    var items = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(ItemPosterBinding.inflate(layoutInflater, parent, false), context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class PosterViewHolder(
    private val binding: ItemPosterBinding,
    private val context: Context,
) : ViewHolder(binding.root) {

    fun bind(item: String) {
        Glide
            .with(context)
            .load(item)
            .placeholder(R.drawable.placeholder_poster)
            .into(binding.posterItem)
    }
}

class DiffCallback : DiffUtil.ItemCallback<Poster>() {

    override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem.url == newItem.url
    }
}