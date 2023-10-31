package com.example.onlinecinema.data.networking.serialization.responses

import com.google.gson.annotations.SerializedName

data class ListWrapper<T>(
    @SerializedName("items")
    val items : T
)