package com.example.onlinecinema.ui.startScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.onlinecinema.R
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.core.getSuccess
import com.example.onlinecinema.domain.enums.TypeFilmsCollections
import com.example.onlinecinema.databinding.FragmentStartBinding
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.ui.adapters.FilmAdapterDelegate
import com.example.onlinecinema.ui.adapters.FilmRecyclerViewAdapter
import com.example.onlinecinema.ui.adapters.toFilmItem
import com.example.onlinecinema.ui.film.FilmFragment
import com.example.onlinecinema.ui.filmCollections.FilmCollectonsFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: FragmentStartBinding

    private var filTop100mRecyclerViewAdapter: FilmRecyclerViewAdapter? = null
    private var filmTop250RecyclerViewAdapter: FilmRecyclerViewAdapter? = null
    private var filmTopAwaitingRecyclerViewAdapter: FilmRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]

        filTop100mRecyclerViewAdapter = FilmRecyclerViewAdapter(object : FilmAdapterDelegate {
            override fun onFilmClicked(film: FilmRecyclerViewAdapter.FilmItem.Film) {
                this@StartFragment.onFilmClicked(film)
            }

            override fun onShowMore() {
                findNavController().navigate(
                    R.id.action_startFragment_to_filmCollectonsFragment,
                    bundleOf(FilmCollectonsFragment.NAVIGATION_KEY to TypeFilmsCollections.Top100.value)
                )
            }

        })
        filmTop250RecyclerViewAdapter = FilmRecyclerViewAdapter(object : FilmAdapterDelegate {
            override fun onFilmClicked(film: FilmRecyclerViewAdapter.FilmItem.Film) {
                this@StartFragment.onFilmClicked(film)
            }

            override fun onShowMore() {
                findNavController().navigate(
                    R.id.action_startFragment_to_filmCollectonsFragment,
                    bundleOf(FilmCollectonsFragment.NAVIGATION_KEY to TypeFilmsCollections.Top250.value)
                )
            }

        })
        filmTopAwaitingRecyclerViewAdapter = FilmRecyclerViewAdapter(object : FilmAdapterDelegate {
            override fun onFilmClicked(film: FilmRecyclerViewAdapter.FilmItem.Film) {
                this@StartFragment.onFilmClicked(film)
            }

            override fun onShowMore() {
                findNavController().navigate(
                    R.id.action_startFragment_to_filmCollectonsFragment,
                    bundleOf(FilmCollectonsFragment.NAVIGATION_KEY to TypeFilmsCollections.AwaitingFilms.value)
                )
            }

        })

        binding.top100FilmRecycler.adapter = filTop100mRecyclerViewAdapter
        binding.top250FilmRecycler.adapter = filmTop250RecyclerViewAdapter
        binding.topAwaitingFilmRecycler.adapter = filmTopAwaitingRecyclerViewAdapter

        lifecycleScope.launch {
            viewModel.top100.collectLatest { result ->

                when (result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        val films : MutableList<FilmRecyclerViewAdapter.FilmItem> = result.data.map {
                            it.toFilmItem()
                        }.toMutableList()
                        films.add(FilmRecyclerViewAdapter.FilmItem.ShowMore)
                        filTop100mRecyclerViewAdapter?.submitList(films)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.top250.collectLatest { result ->

                when (result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        val films : MutableList<FilmRecyclerViewAdapter.FilmItem> = result.data.map {
                            it.toFilmItem()
                        }.toMutableList()
                        films.add(FilmRecyclerViewAdapter.FilmItem.ShowMore)
                        filmTop250RecyclerViewAdapter?.submitList(films)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.topAwaiting.collectLatest { result ->

                when (result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        val films : MutableList<FilmRecyclerViewAdapter.FilmItem> = result.data.map {
                            it.toFilmItem()
                        }.toMutableList()
                        films.add(FilmRecyclerViewAdapter.FilmItem.ShowMore)
                        filmTopAwaitingRecyclerViewAdapter?.submitList(films)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.randomFilm.collectLatest { result ->

                when (result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {
                        binding.apply {
                            Glide.with(root.context)
                                .load(result.data?.posterURL)
                                .centerCrop()
                                .into(recommendedFilmPoster)
                            recommendedFilmName.text = result.data?.nameRu
                            recommendedFilmDescription.text = result.data?.description
                        }
                    }
                }
            }
        }


        binding.top100FilmRecyclerAll.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmCollectonsFragment,
                bundleOf(FilmCollectonsFragment.NAVIGATION_KEY to TypeFilmsCollections.Top100.value)
            )
        }

        binding.top250FilmRecyclerAll.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmCollectonsFragment,
                bundleOf(FilmCollectonsFragment.NAVIGATION_KEY to TypeFilmsCollections.Top250.value)
            )
        }

        binding.topAwaitingFilmRecyclerAll.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmCollectonsFragment,
                bundleOf(FilmCollectonsFragment.NAVIGATION_KEY to TypeFilmsCollections.AwaitingFilms.value)
            )
        }

        binding.recommendedFilmPoster.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmFragment,
                bundleOf(
                    FilmFragment.NAVIGATION_KEY to (viewModel.randomFilm.value.getSuccess()?.kinopoiskId
                        ?: (viewModel.randomFilm.value.getSuccess()?.filmId))
                )
            )
        }

        binding.watchButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmFragment,
                bundleOf(
                    FilmFragment.NAVIGATION_KEY to (viewModel.randomFilm.value.getSuccess()?.kinopoiskId
                        ?: (viewModel.randomFilm.value.getSuccess()?.filmId))
                )
            )
        }

        binding.recommendedFilmName.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmFragment,
                bundleOf(
                    FilmFragment.NAVIGATION_KEY to (viewModel.randomFilm.value.getSuccess()?.kinopoiskId
                        ?: (viewModel.randomFilm.value.getSuccess()?.filmId))
                )
            )
        }

        binding.recommendedFilmDescription.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_filmFragment,
                bundleOf(
                    FilmFragment.NAVIGATION_KEY to (viewModel.randomFilm.value.getSuccess()?.kinopoiskId
                        ?: (viewModel.randomFilm.value.getSuccess()?.filmId))
                )
            )
        }

    }

    private fun onFilmClicked(film: FilmRecyclerViewAdapter.FilmItem.Film){
        findNavController().navigate(
            R.id.action_startFragment_to_filmFragment,
            bundleOf(FilmFragment.NAVIGATION_KEY to (film.id))
        )
    }
}