package com.example.onlinecinema.data.networking.serialization.adapters

import android.net.Uri
import android.util.Log
import com.example.onlinecinema.data.networking.serialization.responses.FilmBody
import com.example.onlinecinema.domain.models.Film
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class FilmBodyAdapter: JsonDeserializer<Film> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Film {
        val body = context?.deserialize<FilmBody>(json, FilmBody::class.java)
        return Film(
            kinopoiskId = body?.kinopoiskId,
            nameRu = body?.nameRu.orEmpty(),
            posterURL = body?.posterUrl?.let { Uri.parse(body.posterUrl) } ?: Uri.EMPTY,
            ratingGoodReview = body?.ratingGoodReview,
            ratingKinopoisk = body?.ratingKinopoisk,
            ratingImdb = body?.ratingImdb,
            ratingFilmCritics = body?.ratingFilmCritics,
            webUrl = body?.webUrl?.let { Uri.parse(body.webUrl) } ?: Uri.EMPTY,
            year = body?.year ?: 0,
            filmLength = body?.filmLength.toString().orEmpty(),
            description = body?.description.orEmpty(),
            type = body?.type.orEmpty(),
            ratingAgeLimits = body?.ratingAgeLimits.toString().orEmpty(),
            countries = body?.countries?.map { it.country.orEmpty() }?.beautifulPrint(),
            genres = body?.genres?.map{it.genres.orEmpty()}?.beautifulPrint(),
            filmId = body?.filmId,
            rating = body?.rating?.toString()
        )
    }

    fun List<String>.beautifulPrint() : String {
        val separator = ", "
        val str = this.joinToString(separator)
        return str
    }
}


















