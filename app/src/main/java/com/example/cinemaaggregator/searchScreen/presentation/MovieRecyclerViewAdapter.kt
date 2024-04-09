package com.example.cinemaaggregator.searchScreen.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.databinding.ItemFilmBinding
import com.example.cinemaaggregator.searchScreen.presentation.model.MoviePartialModel

class MovieRecyclerViewAdapter(
    private val context: Context,
    private val onItemClickListener: (movieId: Int) -> Unit = {}
) : Adapter<MovieViewHolder>() {

    var items = listOf<MoviePartialModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(ItemFilmBinding.inflate(layoutInflater, parent, false), context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onItemClickListener(items[position].id) }
    }
}

class MovieViewHolder(
    private val binding: ItemFilmBinding, private val context: Context
) : ViewHolder(binding.root) {

    fun bind(item: MoviePartialModel) {
        binding.title.text = item.name
        binding.description.text = item.description
        if (item.movieLength != null && item.movieLength > 0) {
            binding.movieLength.text = "${item.movieLength} минут"
        } else {
            binding.movieLength.text = ""
        }
        binding.year.text = item.year.toString()

        Glide.with(context)
            .load(item.poster?.url)
            .centerCrop()
            .placeholder(context.getDrawable(R.drawable.placeholder_poster))
            .into(binding.poster)
    }
}