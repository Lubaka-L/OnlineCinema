package com.example.onlinecinema.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.databinding.FilmItemBinding
import com.example.onlinecinema.domain.models.Film

interface FilmAdapterPagingDelegate {
    fun onFilmClicked(film: Film)
}

class FilmRecyclerViewAdapterPaging(private var delegate: FilmAdapterPagingDelegate) :
    PagingDataAdapter<Film, FilmRecyclerViewAdapterPaging.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            FilmItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), delegate
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.film = getItem(position)
    }


    class ViewHolder(
        private val binding: FilmItemBinding,
        private var delegate: FilmAdapterPagingDelegate
    ) : RecyclerView.ViewHolder(binding.root) {
        var film: Film? = null
            set(value) {
                value?.let { newValue ->
                    field = newValue
                    binding.apply {
                        Glide.with(root.context).load(newValue.posterURL).into(poster)
                        name.text = newValue.nameRu

                        try {
                            val a = newValue.rating.toString()
                            if (a.contains("%")) {
                                val c = "${a.first()}.0"
                                kinopoiskRating.text = c
                            } else if (a == "null") {
                                linearLayout.visibility = View.GONE
                            } else kinopoiskRating.text = newValue.rating

                        } catch (exception: Exception) {
                            ResultWrapper.error<Error>(
                                error = "$exception\n"
                            )
                        }
                        root.setOnClickListener {
                            delegate.onFilmClicked(newValue)
                        }
                    }
                }
            }
    }


    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem.filmId == newItem.filmId
            }

            override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem == newItem
            }
        }

    }
}