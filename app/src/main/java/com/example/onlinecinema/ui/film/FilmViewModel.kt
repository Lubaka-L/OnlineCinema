package com.example.onlinecinema.ui.film

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinecinema.App
import com.example.onlinecinema.core.ListWrapperItem
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.domain.models.Budget
import com.example.onlinecinema.domain.models.Director
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.domain.models.SequelsPrequels
import com.example.onlinecinema.domain.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmViewModel : ViewModel() {

    private val _film: MutableStateFlow<ResultWrapper<Film?>> =
        MutableStateFlow(ResultWrapper.loading())
    val film: StateFlow<ResultWrapper<Film?>> = _film.asStateFlow()

    private val _director: MutableStateFlow<ResultWrapper<ListWrapperItem<List<Director>>>> =
        MutableStateFlow(ResultWrapper.loading())
    val director: StateFlow<ResultWrapper<ListWrapperItem<List<Director>>>> = _director.asStateFlow()

    private val _budget: MutableStateFlow<ResultWrapper<ListWrapperItem<List<Budget>>>> =
        MutableStateFlow(ResultWrapper.loading())
    val budget: StateFlow<ResultWrapper<ListWrapperItem<List<Budget>>>> = _budget.asStateFlow()

    private val _similarFilms: MutableStateFlow<ResultWrapper<ListWrapperItem<List<Film>>>> =
        MutableStateFlow(ResultWrapper.loading())
    val similarFilms: StateFlow<ResultWrapper<ListWrapperItem<List<Film>>>> = _similarFilms.asStateFlow()

    private val _sequelsPrequels: MutableStateFlow<ResultWrapper<List<SequelsPrequels?>>> =
        MutableStateFlow(ResultWrapper.loading())
    val sequelsPrequels: StateFlow<ResultWrapper<List<SequelsPrequels?>>> =
        _sequelsPrequels.asStateFlow()

    @Inject
    lateinit var repository: Repository

    init {
        App.component.inject(this)
    }

    fun loadFilm(id: Long) {
        viewModelScope.launch {
            _film.update { ResultWrapper.loading() }
            _film.update {
                repository.getFilm(id)
            }
        }
    }

    fun loadDirector(id: Long) {
        viewModelScope.launch {
            _director.update { ResultWrapper.loading() }
            _director.update {
                val directorRes: ResultWrapper<ListWrapperItem<List<Director>>> = repository.getDirector(id)
                when(directorRes){
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Load -> {}
                    is ResultWrapper.Success -> {}
                }
                directorRes
            }
        }
    }

    fun loadBudget(id: Long) {
        viewModelScope.launch {
            _budget.update { ResultWrapper.loading() }
            _budget.update {
                repository.getBudget(id)
            }
        }
    }

    fun loadSimilarFilms(id: Long) {
        viewModelScope.launch {
            _similarFilms.update { ResultWrapper.loading() }
            _similarFilms.update {
                repository.getSimilarFilms(id)
            }
        }
    }

    fun loadSequelsPrequels(id: Long) {
        viewModelScope.launch {
            _sequelsPrequels.update { ResultWrapper.loading() }
            _sequelsPrequels.update {
                repository.getFilmsSequelsPrequels(id)
            }
        }
    }

}