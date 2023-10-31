package com.example.onlinecinema.data.repository

import androidx.paging.*
import com.example.onlinecinema.core.ListWrapper
import com.example.onlinecinema.core.ListWrapperItem
import com.example.onlinecinema.core.ResponseExtension.handleResponse
import com.example.onlinecinema.core.ResponseExtension.returnSafely
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.data.networking.api.FilmsApi
import com.example.onlinecinema.data.repository.dataSource.Top100DataSource
import com.example.onlinecinema.data.repository.dataSource.Top250DataSource
import com.example.onlinecinema.data.repository.dataSource.TopAwaitDataSource
import com.example.onlinecinema.domain.models.Budget
import com.example.onlinecinema.domain.models.Director
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.domain.models.SequelsPrequels
import com.example.onlinecinema.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val filmsApi: FilmsApi) : Repository {

    private val _isOverflowException: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isOverflowException: StateFlow<Boolean> = _isOverflowException.asStateFlow()


    override fun getTop100Pager(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            Top100DataSource(this)
        }.flow
    }

    override fun getTop250Pager(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            Top250DataSource(this)
        }.flow
    }

    override fun getTopAwaitPager(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            TopAwaitDataSource(this)
        }.flow
    }


    override suspend fun getAwaitFilms(page: Int): ResultWrapper<ListWrapper<List<Film>>> {
        return filmsApi.getAwaitingFilms(page).handleResponse(_isOverflowException)
    }

    override suspend fun get250Films(page: Int): ResultWrapper<ListWrapper<List<Film>>> {
        return filmsApi.getTop250Films(page).handleResponse(_isOverflowException)
    }

    override suspend fun get100Films(page: Int): ResultWrapper<ListWrapper<List<Film>>> {
        return filmsApi.getTop100Films(page).handleResponse(_isOverflowException)
    }

    override suspend fun getFilm(id: Long): ResultWrapper<Film> {
        return try {
            when (val result = filmsApi.getFilm(id).handleResponse(_isOverflowException)) {
                is ResultWrapper.Success -> ResultWrapper.success(result.data)
                is ResultWrapper.Load -> ResultWrapper.loading()
                is ResultWrapper.Error -> ResultWrapper.error(result.error)
            }
        } catch (exception: Exception) {
            ResultWrapper.error(
                error = "$exception\n"
            )
        }
    }

    override suspend fun getBudget(id: Long): ResultWrapper<ListWrapperItem<List<Budget>>> {
        return returnSafely {
            filmsApi.getBudget(id).handleResponse(_isOverflowException)
        }
    }

    override suspend fun getDirector(id: Long): ResultWrapper<ListWrapperItem<List<Director>>> {
        return returnSafely {
            filmsApi.getDirector(id).handleResponse(_isOverflowException)
        }
    }

    override suspend fun getSimilarFilms(id: Long): ResultWrapper<ListWrapperItem<List<Film>>> {
        return returnSafely {
            filmsApi.getSimilarFilms(id).handleResponse(_isOverflowException)
        }
    }

    override suspend fun getFilmsSequelsPrequels(id: Long): ResultWrapper<List<SequelsPrequels>> {
        return returnSafely {
            filmsApi.getFilmsSequelsPrequels(id).handleResponse(_isOverflowException)
        }
    }

    override suspend fun get100Films(): ResultWrapper<List<Film>> {
        return when (val result = filmsApi.getTop100Films().handleResponse(_isOverflowException)) {
            is ResultWrapper.Success -> ResultWrapper.success(result.data.list)
            is ResultWrapper.Load -> ResultWrapper.loading()
            is ResultWrapper.Error -> ResultWrapper.error(result.error)
        }
    }

    override suspend fun get250Films(): ResultWrapper<List<Film>> {
        return when (val result = filmsApi.getTop250Films().handleResponse(_isOverflowException)) {
            is ResultWrapper.Success -> ResultWrapper.success(result.data.list)
            is ResultWrapper.Load -> ResultWrapper.loading()
            is ResultWrapper.Error -> ResultWrapper.error(result.error)
        }
    }

    override suspend fun getAwaitingFilms(): ResultWrapper<List<Film>> {
        return when (val result =
            filmsApi.getAwaitingFilms().handleResponse(_isOverflowException)) {
            is ResultWrapper.Success -> ResultWrapper.success(result.data.list)
            is ResultWrapper.Load -> ResultWrapper.loading()
            is ResultWrapper.Error -> ResultWrapper.error(result.error)
        }
    }
}