package com.example.onlinecinema.domain.models

import android.net.Uri
import retrofit2.http.Url

data class SequelsPrequels(

    val filmId: Long,
    val nameRu: String,
    val posterUrl: Uri,
    val relationType: String

)