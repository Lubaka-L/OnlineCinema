package com.example.onlinecinema.core

import com.google.gson.annotations.SerializedName

data class ListWrapper<T>(
    @SerializedName("pagesCount")
    val pagesCount: Int,
    @SerializedName("films")
    val list: T
)

data class ListWrapperItem<T>(
    @SerializedName("items")
    val items: T
)
