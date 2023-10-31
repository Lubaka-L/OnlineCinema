package com.example.onlinecinema.data.networking.api

import com.example.onlinecinema.core.ListWrapper
import com.example.onlinecinema.core.ListWrapperItem
import com.example.onlinecinema.domain.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilm(@Path("id") id: Long): Response<Film>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/{id}/box_office")
    suspend fun getBudget(@Path("id") id: Long): Response<ListWrapperItem<List<Budget>>>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/{id}/awards")
    suspend fun getDirector(@Path("id") id: Long): Response<ListWrapperItem<List<Director>>>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(@Path("id") id: Long): Response<ListWrapperItem<List<Film>>>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.1/films/{id}/sequels_and_prequels")
    suspend fun getFilmsSequelsPrequels(@Path("id") id: Long): Response<List<SequelsPrequels>>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/top?type=TOP_AWAIT_FILMS")
    suspend fun getAwaitingFilms(
        @Query("page") page: Int = 1
    ): Response<ListWrapper<List<Film>>>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTop100Films(
        @Query("page") page: Int = 1
    ): Response<ListWrapper<List<Film>>>

    @Headers("X-API-KEY:2a923e69-664a-474b-9d20-b150b1ca1f2e")
    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun getTop250Films(
        @Query("page") page: Int = 1
    ): Response<ListWrapper<List<Film>>>

}