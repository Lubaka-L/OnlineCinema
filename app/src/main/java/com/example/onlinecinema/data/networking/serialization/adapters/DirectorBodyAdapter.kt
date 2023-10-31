package com.example.onlinecinema.data.networking.serialization.adapters

import com.example.onlinecinema.data.networking.serialization.responses.DirectorBody
import com.example.onlinecinema.domain.models.Budget
import com.example.onlinecinema.domain.models.Director
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DirectorBodyAdapter : JsonDeserializer<Director> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Director {
        val body = context?.deserialize<DirectorBody>(json, DirectorBody::class.java)
        return Director(
            directorsName = body?.persons?.firstOrNull()?.nameRu.orEmpty()
        )
    }
}