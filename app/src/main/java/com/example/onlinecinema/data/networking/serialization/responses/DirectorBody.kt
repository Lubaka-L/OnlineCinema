package com.example.onlinecinema.data.networking.serialization.responses

import com.google.gson.annotations.SerializedName

data class DirectorBody(

    @SerializedName("persons")
    val persons: List<Persons>?

) {
    data class Persons(
        @SerializedName("nameRu")
        val nameRu: String?
    )

}