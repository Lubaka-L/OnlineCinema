package com.example.onlinecinema.data.networking.serialization.responses

import com.google.gson.annotations.SerializedName

data class FilmBody(

    @SerializedName("kinopoiskId")
    val kinopoiskId: Long?,

    @SerializedName("nameRu")
    val nameRu: String?,

    @SerializedName("posterUrl")
    val posterUrl: String?,

    @SerializedName("ratingGoodReview")
    val ratingGoodReview: Double?,

    @SerializedName("ratingKinopoisk")
    val ratingKinopoisk: Double?,

    @SerializedName("rating")
    val rating: Any?,

    @SerializedName("ratingImdb")
    val ratingImdb: Double?,

    @SerializedName("ratingFilmCritics")
    val ratingFilmCritics: Double?,

    @SerializedName("webUrl")
    val webUrl: String?,

    @SerializedName("year")
    val year: Int?,

    @SerializedName("filmLength")
    val filmLength: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("ratingAgeLimits")
    val ratingAgeLimits: String?,

    @SerializedName("countries")
    val countries: List<Countries>?,

    @SerializedName("genres")
    val genres: List<Genres>?,

    @SerializedName("filmId")
    val filmId : Long?

) {

    data class Countries(
        @SerializedName("country")
        val country: String?
    )

    data class Genres(
        @SerializedName("genre")
        val genres: String?
    )

}