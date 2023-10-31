package com.example.onlinecinema.data.repository.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.onlinecinema.core.ResultWrapper
import com.example.onlinecinema.domain.models.Film
import com.example.onlinecinema.domain.repository.Repository

class Top100DataSource(
    val repository: Repository
) : PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val key = params.key ?: 1

        return when(val filmsResult = repository.get100Films(key)){
            is ResultWrapper.Success -> {
               LoadResult.Page(
                   data = filmsResult.data.list,
                   prevKey = if (key == 1) null else key - 1,
                   nextKey = if (filmsResult.data.pagesCount == key) null else key + 1
               )
            }
            is ResultWrapper.Load -> {
                LoadResult.Error(Throwable())
            }
            is ResultWrapper.Error -> {
                LoadResult.Error(Throwable(filmsResult.error))
            }
        }
    }
}

class Top250DataSource(
    val repository: Repository
) : PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val key = params.key ?: 1

        return when(val filmsResult = repository.get250Films(key)){
            is ResultWrapper.Success -> {
                LoadResult.Page(
                    data = filmsResult.data.list,
                    prevKey = if (key == 1) null else key - 1,
                    nextKey = if (filmsResult.data.pagesCount == key) null else key + 1
                )
            }
            is ResultWrapper.Load -> {
                LoadResult.Error(Throwable())
            }
            is ResultWrapper.Error -> {
                LoadResult.Error(Throwable(filmsResult.error))
            }
        }
    }
}

class TopAwaitDataSource(
    val repository: Repository
) : PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val key = params.key ?: 1

        return when(val filmsResult = repository.getAwaitFilms(key)){
            is ResultWrapper.Success -> {
                LoadResult.Page(
                    data = filmsResult.data.list,
                    prevKey = if (key == 1) null else key - 1,
                    nextKey = if (filmsResult.data.pagesCount == key) null else key + 1
                )
            }
            is ResultWrapper.Load -> {
                LoadResult.Error(Throwable())
            }
            is ResultWrapper.Error -> {
                LoadResult.Error(Throwable(filmsResult.error))
            }
        }
    }
}