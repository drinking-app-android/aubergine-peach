package com.example.drinkify.ui

import com.example.drinkify.uhm.Post
import retrofit2.Call
import retrofit2.http.GET


interface JsonPlaceHolderApi {
    @get:GET("posts")
    val posts: Call<List<Post>>
}