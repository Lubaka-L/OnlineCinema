package com.example.onlinecinema.ui.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinecinema.databinding.FilmItemBinding
import com.example.onlinecinema.databinding.ItemSimilarFilmBinding
import com.example.onlinecinema.domain.models.Film

interface SimilarFilmAdapterDelegate {
    fun onSimilarFilmClicked(film: Film)
}

    class SimilarFilmsRecyclerViewAdapter(private var delegate: SimilarFilmAdapterDelegate) :
        ListAdapter<Film, SimilarFilmsRecyclerViewAdapter.ViewHolder>(
            diffUtil
        ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemSimilarFilmBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), delegate
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.similarFilm = getItem(position)
        }

        class ViewHolder(
            private val binding: ItemSimilarFilmBinding,
            private var delegate: SimilarFilmAdapterDelegate
        ) : RecyclerView.ViewHolder(binding.root) {

            var similarFilm: Film? = null
                set(value) {
                    value?.let { newValue ->
                        field = newValue

                        binding.apply {
                            Glide.with(root.context).load(newValue.posterURL).into(poster)
                            name.text = newValue.nameRu
                            ratingKinopoisk.text = newValue.ratingKinopoisk?.toString() ?: newValue.ratingImdb?.toString() ?: newValue.ratingFilmCritics.toString()
                            ratingBar.rating = newValue.ratingKinopoisk?.toFloat() ?: 0.0F

                            root.setOnClickListener {
                                delegate.onSimilarFilmClicked(newValue)
                            }
                        }
                    }
                }
        }

        companion object {

            var diffUtil = object : DiffUtil.ItemCallback<Film>() {
                override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
                    return oldItem.kinopoiskId == newItem.kinopoiskId
                }

                override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
                    return oldItem == newItem
                }
            }

        }

    }
