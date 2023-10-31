package com.example.onlinecinema.data.networking.serialization.adapters

import android.net.Uri
import com.example.onlinecinema.data.networking.serialization.responses.SequelsPrequelsBody
import com.example.onlinecinema.domain.models.SequelsPrequels
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class SequelsPrequelsBodyAdapter : JsonDeserializer<SequelsPrequels>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SequelsPrequels {
        val body = context?.deserialize<SequelsPrequelsBody>(json, SequelsPrequelsBody::class.java)
        return SequelsPrequels(
            filmId = body?.filmId ?: 0,
            nameRu = body?.nameRu.orEmpty(),
            posterUrl = body?.posterUrl?.let { Uri.parse(body.posterUrl) } ?: Uri.EMPTY,
            relationType = body?.relationType.orEmpty()
        )
    }
}