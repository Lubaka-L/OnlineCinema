package com.example.onlinecinema.ui.filmCollections

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.onlinecinema.R
import com.example.onlinecinema.databinding.FragmentFilmCollectonsBinding
import com.example.onlinecinema.domain.enums.TypeFilmsCollections
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.ui.adapters.*
import com.example.onlinecinema.ui.film.FilmFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class FilmCollectonsFragment : Fragment(), FilmAdapterPagingDelegate {

    private lateinit var viewModel: FilmCollectonsViewModel
    private lateinit var binding: FragmentFilmCollectonsBinding

    private var filTopRecyclerViewAdapter: FilmRecyclerViewAdapterPaging? = null

    companion object {
        val NAVIGATION_KEY = "FilmCollectionsFragmentArgs"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmCollectonsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FilmCollectonsViewModel::class.java]

        val id = arguments?.getLong(NAVIGATION_KEY) ?: return

        filTopRecyclerViewAdapter = FilmRecyclerViewAdapterPaging(this)
        binding.topRecycler.adapter = filTopRecyclerViewAdapter

        lifecycleScope.launch {
            when (id) {
                TypeFilmsCollections.Top100.value -> {
                    viewModel.loadTop100().collectLatest { result ->
                        filTopRecyclerViewAdapter?.submitData(result)
                    }
                }
                TypeFilmsCollections.Top250.value -> {
                    viewModel.loadTop250().collectLatest { result ->
                        filTopRecyclerViewAdapter?.submitData(result)
                    }
                }
                TypeFilmsCollections.AwaitingFilms.value -> {
                    viewModel.loadTopAwaiting().collectLatest { result ->
                        filTopRecyclerViewAdapter?.submitData(result)
                    }
                }
            }
        }
    }

    override fun onFilmClicked(film: Film) {
        findNavController().navigate(
            R.id.action_filmCollectonsFragment_to_filmFragment,
            bundleOf(FilmFragment.NAVIGATION_KEY to (film.kinopoiskId ?: film.filmId))
        )
    }
}

