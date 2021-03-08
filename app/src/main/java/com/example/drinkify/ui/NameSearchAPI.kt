package com.example.drinkify.ui


import com.example.drinkify.uhm.Drink
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface NameSearchAPI {
    @get:GET("search.php?s={name}")
     val drinks: Call<Drink>
     fun searchName(@Query("name")name: String)
}
