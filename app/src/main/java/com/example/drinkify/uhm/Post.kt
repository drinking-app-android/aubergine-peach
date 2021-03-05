package com.example.drinkify.uhm

import com.google.gson.annotations.SerializedName


class Post {
    val userId = 0
    val id = 0
    val title: String? = null

    @SerializedName("body")
    val text: String? = null
}