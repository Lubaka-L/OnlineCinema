package com.example.onlinecinema.domain.models

import android.net.Uri

data class Film(

    val kinopoiskId: Long?,
    val nameRu: String?,
    val posterURL: Uri?,
    val ratingGoodReview: Double?,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?,
    val ratingFilmCritics: Double?,
    val webUrl: Uri?,
    val year: Int?,
    val filmLength: String?,
    val description: String?,
    val type: String?,
    val ratingAgeLimits: String?,
    val countries: String?,
    val genres: String?,

    val filmId : Long?,
    val rating : String?

)