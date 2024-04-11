package com.example.cinemaaggregator.movieScreen.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemaaggregator.R
import com.example.cinemaaggregator.databinding.ItemReviewBinding
import com.example.cinemaaggregator.movieScreen.domain.model.Review

class ReviewAdapter(
    private val context: Context
) : Adapter<ReviewsViewHolder>() {

    var items = listOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReviewsViewHolder(ItemReviewBinding.inflate(layoutInflater, parent, false), context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class ReviewsViewHolder(
    private val binding: ItemReviewBinding,
    private val context: Context
) : ViewHolder(binding.root) {

    fun bind(item: Review) {
        if (item.title.isNullOrEmpty())
            binding.title.visibility = View.GONE
        binding.title.text = item.title
        binding.authorRight.text = item.author
        binding.review.text = item.review
        if (item.type == "Позитивный")
            binding.root.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.green))
        if (item.type == "Негативный")
            binding.root.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.red))
    }
}