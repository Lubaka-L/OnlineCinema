package com.example.onlinecinema.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.onlinecinema.core.ListWrapper
import com.example.onlinecinema.core.ListWrapperItem
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.domain.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

interface Repository {

    val isOverflowException: StateFlow<Boolean>

    suspend fun getFilm(id: Long): ResultWrapper<Film>
    suspend fun getBudget(id: Long): ResultWrapper<ListWrapperItem<List<Budget>>>
    suspend fun getDirector(id: Long): ResultWrapper<ListWrapperItem<List<Director>>>
    suspend fun getSimilarFilms(id: Long): ResultWrapper<ListWrapperItem<List<Film>>>
    suspend fun getFilmsSequelsPrequels(id: Long): ResultWrapper<List<SequelsPrequels>>
    suspend fun get100Films(): ResultWrapper<List<Film>>
    suspend fun get250Films(): ResultWrapper<List<Film>>
    suspend fun getAwaitingFilms(): ResultWrapper<List<Film>>

    suspend fun get100Films(page: Int): ResultWrapper<ListWrapper<List<Film>>>
    fun getTop100Pager(): Flow<PagingData<Film>>

    suspend fun get250Films(page: Int): ResultWrapper<ListWrapper<List<Film>>>
    fun getTop250Pager(): Flow<PagingData<Film>>

    suspend fun getAwaitFilms(page: Int): ResultWrapper<ListWrapper<List<Film>>>
    fun getTopAwaitPager(): Flow<PagingData<Film>>

}