package com.sdk.movieapp.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdk.movieapp.databinding.MovieLayoutBinding
import com.sdk.movieapp.model.Result
import com.sdk.movieapp.util.Constants
import com.squareup.picasso.Picasso

class MovieAdapter : ListAdapter<Result, MovieAdapter.MovieViewHolder>(DiffCallBack()) {

    lateinit var onClick: (Result) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(private val binding: MovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.apply {
                textView.text = result.original_title
                textRank.text = result.vote_average.toString()
                textLan.text = "Language: ${result.original_language}"

                val image = "${Constants.BASE_IMG}${result.poster_path}"

                Picasso.get()
                    .load(image)
                    .into(binding.imageView)
            }
            itemView.setOnClickListener {
                onClick.invoke(result)
            }
        }
    }
}