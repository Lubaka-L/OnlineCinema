package com.example.onlinecinema.data.networking.serialization.responses

import com.google.gson.annotations.SerializedName

data class BudgetBody(

    @SerializedName("amount")
    val amount: Long?,

    @SerializedName("symbol")
    val symbol: String?
)