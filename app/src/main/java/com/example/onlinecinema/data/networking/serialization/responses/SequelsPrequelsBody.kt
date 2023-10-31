package com.example.onlinecinema.data.networking.serialization.responses

import com.google.gson.annotations.SerializedName

data class SequelsPrequelsBody(

    @SerializedName("filmId")
    val filmId: Long?,

    @SerializedName("nameRu")
    val nameRu: String?,

    @SerializedName("posterUrl")
    val posterUrl: String?,

    @SerializedName("relationType")
    val relationType: String?

)