package com.example.onlinecinema.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinecinema.databinding.ItemSimilarFilmBinding
import com.example.onlinecinema.domain.models.SequelsPrequels

interface SequelsPrequelsFilmAdapterDelegate {
    fun onSequelsPrequelsFilmClicked(sequelsPrequels: SequelsPrequels)
}

class SequelsPrequelsRecyclerViewAdapter(private var delegate: SequelsPrequelsFilmAdapterDelegate) :
    ListAdapter<SequelsPrequels, SequelsPrequelsRecyclerViewAdapter.ViewHolder>(diffUtil) {

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
        holder.sequelsPrequelsFilm = getItem(position)
    }


    class ViewHolder(
        private val binding: ItemSimilarFilmBinding,
        private var delegate: SequelsPrequelsFilmAdapterDelegate
    ) : RecyclerView.ViewHolder(binding.root) {

        var sequelsPrequelsFilm: SequelsPrequels? = null
            set(value) {
                value?.let { newValue ->
                    field = newValue

                    binding.apply {
                        Glide.with(root.context).load(newValue.posterUrl).into(poster)
                        name.text = newValue.nameRu
                        root.setOnClickListener {
                            delegate.onSequelsPrequelsFilmClicked(newValue)
                        }
                    }
                }
            }
    }


    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<SequelsPrequels>() {

            override fun areItemsTheSame(
                oldItem: SequelsPrequels,
                newItem: SequelsPrequels
            ): Boolean {
                return oldItem.filmId == newItem.filmId
            }

            override fun areContentsTheSame(
                oldItem: SequelsPrequels,
                newItem: SequelsPrequels
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}
