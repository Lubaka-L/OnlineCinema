package com.example.onlinecinema.ui.startScreen

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinecinema.App
import com.example.onlinecinema.R
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.core.getSuccess
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.domain.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class StartViewModel : ViewModel() {

    private val _top100: MutableStateFlow<ResultWrapper<List<Film>>> =
        MutableStateFlow(ResultWrapper.loading())
    val top100: StateFlow<ResultWrapper<List<Film>>> = _top100.asStateFlow()

    private val _top250: MutableStateFlow<ResultWrapper<List<Film>>> =
        MutableStateFlow(ResultWrapper.loading())
    val top250: StateFlow<ResultWrapper<List<Film>>> = _top250.asStateFlow()

    private val _topAwaiting: MutableStateFlow<ResultWrapper<List<Film>>> =
        MutableStateFlow(ResultWrapper.loading())
    val topAwaiting: StateFlow<ResultWrapper<List<Film>>> = _topAwaiting.asStateFlow()

    private val _randomFilm: MutableStateFlow<ResultWrapper<Film>> =
        MutableStateFlow(ResultWrapper.loading())
    val randomFilm: StateFlow<ResultWrapper<Film>> = _randomFilm.asStateFlow()

    private val _searchedFilm: MutableStateFlow<ResultWrapper<Film>> =
        MutableStateFlow(ResultWrapper.loading())
    val searchedFilm: StateFlow<ResultWrapper<Film>> = _searchedFilm.asStateFlow()

    @Inject
    lateinit var repository: Repository

    init {
        App.component.inject(this)
        loadTop100()
        loadTop250()
        loadTopAwaiting()
    }

    fun loadTop100() {
        viewModelScope.launch {
                val top100 = repository.get100Films()
                _top100.update {
                    top100
                }
            val top100List: List<Film>? = top100.getSuccess()
            if (!top100List.isNullOrEmpty()) {
                val randomIndex = Random.nextInt(top100List.size - 1)
                val randomElement = top100List[randomIndex]
                val randomFilm: Film = randomElement
                _randomFilm.update { ResultWrapper.success(randomFilm) }
            }
        }
    }

    fun loadTop250() {
        viewModelScope.launch {
            _top250.update {
                repository.get250Films()
            }
        }
    }

    fun loadTopAwaiting() {
        viewModelScope.launch {
            _topAwaiting.update {
                repository.getAwaitingFilms()
            }
        }
    }

}