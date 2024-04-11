package com.example.cinemaaggregator.movieScreen.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinemaaggregator.databinding.ItemActorBinding
import com.example.cinemaaggregator.movieScreen.domain.model.Actor

class ActorsAdapter(
    private val context: Context
) : Adapter<ActorsViewHolder>() {

    var items = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActorsViewHolder(ItemActorBinding.inflate(layoutInflater, parent, false), context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class ActorsViewHolder(
    private val binding: ItemActorBinding,
    private val context: Context
) : ViewHolder(binding.root) {

    fun bind(item: Actor) {
        binding.name.text = item.name
        binding.description.text = item.description
        Glide
            .with(context)
            .load(item.photoUrl)
            .into(binding.photo)
    }
}