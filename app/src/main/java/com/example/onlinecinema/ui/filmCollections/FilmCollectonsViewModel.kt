package com.example.onlinecinema.ui.filmCollections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.onlinecinema.App
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.domain.repository.Repository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmCollectonsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.component.inject(this)
    }

    fun loadTop100() : Flow<PagingData<Film>>{
        return repository.getTop100Pager().cachedIn(viewModelScope)
    }

    fun loadTop250(): Flow<PagingData<Film>>{
        return repository.getTop250Pager().cachedIn(viewModelScope)
    }

    fun loadTopAwaiting(): Flow<PagingData<Film>>{
        return repository.getTopAwaitPager().cachedIn(viewModelScope)
    }

}