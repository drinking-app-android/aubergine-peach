package com.example.drinkify.ui

import com.example.drinkify.uhm.Drink
import retrofit2.Call
import retrofit2.http.GET

interface RandomDrinkAPI {
    @get:GET("random.php")
    val drinks: Call<Drink>
}
