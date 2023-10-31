package com.example.onlinecinema.data.networking.serialization.adapters

import com.example.onlinecinema.data.networking.serialization.responses.BudgetBody
import com.example.onlinecinema.domain.models.Budget
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class BudgetBodyAdapter : JsonDeserializer<Budget> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Budget {
        val body = context?.deserialize<BudgetBody>(json, BudgetBody::class.java)
        return Budget(
            amount = body?.amount ?: 0,
            symbol = body?.symbol.orEmpty()
        )
    }
}