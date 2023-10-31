package com.example.onlinecinema.ui.film

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.onlinecinema.R
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.databinding.FragmentFilmBinding
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.domain.models.SequelsPrequels
import com.example.onlinecinema.ui.adapters.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FilmFragment : Fragment(), SimilarFilmAdapterDelegate, SequelsPrequelsFilmAdapterDelegate {

    private lateinit var viewModel: FilmViewModel
    private lateinit var binding: FragmentFilmBinding

    private var similarFilmRecyclerViewAdapter: SimilarFilmsRecyclerViewAdapter? = null
    private var sequelsPrequelsFilmRecyclerViewAdapter: SequelsPrequelsRecyclerViewAdapter? = null

    companion object {
        val NAVIGATION_KEY = "FilmFragmentArgs"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FilmViewModel::class.java]
        val id = arguments?.getLong(NAVIGATION_KEY) ?: return
        viewModel.loadFilm(id)
        viewModel.loadBudget(id)
        viewModel.loadDirector(id)
        viewModel.loadSequelsPrequels(id)
        viewModel.loadSimilarFilms(id)

        similarFilmRecyclerViewAdapter = SimilarFilmsRecyclerViewAdapter(this)
        sequelsPrequelsFilmRecyclerViewAdapter = SequelsPrequelsRecyclerViewAdapter(this)

        binding.similarFilmsRecycler.adapter = similarFilmRecyclerViewAdapter
        binding.sequelsFilmsRecycler.adapter = sequelsPrequelsFilmRecyclerViewAdapter

        lifecycleScope.launch {
            viewModel.film.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {

                        val ratingKinopoisk = result.data?.ratingKinopoisk
                        val ratingImdb = result.data?.ratingImdb

                        if (ratingKinopoisk == null || ratingImdb == null) {
                        } else binding.ratingCard.visibility = View.VISIBLE

                        binding.collapsingToolbar.title = result.data?.nameRu

                        binding.apply {

                            Glide.with(root.context)
                                .load(result.data?.posterURL)
                                .centerCrop()
                                .into(posterMain)

                            allowedAge.text = if (result.data?.ratingAgeLimits.isNullOrEmpty()) {
                                "0+"
                            } else
                                "${result.data?.ratingAgeLimits.toString().drop(3)} +"

                            name.text = result.data?.nameRu

                            watchButton.setOnClickListener {
                                val URL = result.data?.webUrl
                                startActivity(Intent(Intent.ACTION_VIEW, URL))
                            }

                            val res = result.data?.ratingKinopoisk?.toFloat()
                            val doubRes = res?.div(2)
                            ratingBar.rating = doubRes ?: 0.0F

                            kinopoiskRatingCount.text = result.data?.ratingKinopoisk.toString()

                            imdbRatingCount.text = result.data?.ratingImdb.toString()

                            date.text = result.data?.year.toString()

                            country.text = result.data?.countries

                            time.text = "${result.data?.filmLength} минут"

                            descriptionText.text = result.data?.description

                            ganre.text = result.data?.genres

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.director.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        if ((result.data.items as? List<Any>).isNullOrEmpty()) {
                            binding.directorTitle.visibility = View.GONE
                            binding.directorLayout.visibility = View.GONE
                        } else {
                            binding.directorName.text = result.data.items.first().directorsName
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.budget.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Error -> {
                        binding.budgetIcon.visibility = View.GONE
                    }
                    is ResultWrapper.Load -> {}

                    is ResultWrapper.Success -> {
                        if ((result.data.items as? List<Any>).isNullOrEmpty()) {
                            binding.budgetIcon.visibility = View.GONE
                        } else {
                            binding.budget.text =
                                "${result.data.items.first().amount} ${result.data.items.first().symbol}"
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.similarFilms.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Error -> {
                        binding.similarFilmsTitle.visibility = View.GONE
                    }
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        if (result.data.items.isNullOrEmpty()) {
                            binding.similarFilmsTitle.visibility = View.GONE
                        } else {
                            similarFilmRecyclerViewAdapter?.submitList(result.data.items)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.sequelsPrequels.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Error -> {
                        binding.sequelsPrequelsFilmsTitle.visibility = View.GONE
                    }
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            binding.sequelsPrequelsFilmsTitle.visibility = View.GONE
                        } else {
                            sequelsPrequelsFilmRecyclerViewAdapter?.submitList(result.data)
                        }
                    }
                }
            }
        }

    }


    override fun onSequelsPrequelsFilmClicked(sequelsPrequels: SequelsPrequels) {
        findNavController().navigate(
            R.id.action_filmFragment_to_filmFragment,
            bundleOf(NAVIGATION_KEY to (sequelsPrequels.filmId))
        )
    }

    override fun onSimilarFilmClicked(film: Film) {
        findNavController().navigate(
            R.id.action_filmFragment_to_filmFragment,
            bundleOf(NAVIGATION_KEY to (film.filmId ?: film.kinopoiskId))
        )
    }

}