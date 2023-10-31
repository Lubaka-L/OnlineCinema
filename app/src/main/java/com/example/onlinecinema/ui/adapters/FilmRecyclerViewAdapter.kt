package com.example.onlinecinema.ui.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.domain.enums.TypeFilmsCollections
import com.example.onlinecinema.databinding.FilmItemBinding
import com.example.onlinecinema.databinding.ItemShowAllBinding
import com.example.onlinecinema.domain.models.Film
import kotlin.Unit.toString

interface FilmAdapterDelegate {
    fun onFilmClicked(film: FilmRecyclerViewAdapter.FilmItem.Film)
    fun onShowMore()
}

class FilmRecyclerViewAdapter(private var delegate: FilmAdapterDelegate) :
    ListAdapter<FilmRecyclerViewAdapter.FilmItem, FilmRecyclerViewAdapter.ViewHolder>(diffUtil) {

    sealed class FilmItem {
        data class Film(
            val id: Long,
            val posterUrl: Uri,
            val name: String,
            val rating: String?
        ) : FilmItem()

        object ShowMore : FilmItem()
    }

    enum class FilmItemType(val itemId: Int) {
        Film(1),
        ShowMore(2)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FilmItem.Film -> FilmItemType.Film.itemId
            is FilmItem.ShowMore -> FilmItemType.ShowMore.itemId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            FilmItemType.Film.itemId -> {
                ViewHolder.FilmViewHolder(
                    FilmItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), delegate
                )
            }
            else -> {
                ViewHolder.ShowMoreViewHolder(
                    ItemShowAllBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), delegate
                )
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.FilmViewHolder -> {
                holder.film = getItem(position) as? FilmItem.Film
            }
            is ViewHolder.ShowMoreViewHolder -> {
                holder.init()
            }
        }
    }

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        class FilmViewHolder(
            private val binding: FilmItemBinding,
            private var delegate: FilmAdapterDelegate
        ) : ViewHolder(binding.root) {
            var film: FilmItem.Film? = null
                set(value) {
                    value?.let { newValue ->
                        field = newValue
                        binding.apply {
                            Glide.with(root.context).load(newValue.posterUrl).into(poster)
                            name.text = newValue.name

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

        class ShowMoreViewHolder(
            private val binding: ItemShowAllBinding,
            private var delegate: FilmAdapterDelegate
        ) : ViewHolder(binding.root) {
            fun init() {
                binding.root.setOnClickListener { delegate.onShowMore() }
            }
        }
    }

    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<FilmItem>() {
            override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
                return if (oldItem is FilmItem.Film && newItem is FilmItem.Film) {
                    oldItem.id == newItem.id
                } else oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
                return oldItem == newItem
            }
        }

    }
}

fun Film.toFilmItem(): FilmRecyclerViewAdapter.FilmItem.Film {
    return FilmRecyclerViewAdapter.FilmItem.Film(
        id = filmId ?: kinopoiskId ?: -1,
        posterUrl = posterURL ?: Uri.EMPTY,
        name = nameRu.orEmpty(),
        rating = (ratingKinopoisk ?: ratingImdb ?: rating)?.toString()
    )
}