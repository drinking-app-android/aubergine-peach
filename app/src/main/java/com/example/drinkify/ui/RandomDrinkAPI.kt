package com.example.drinkify.ui

import com.example.drinkify.model.Drink
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

data class DrinkHolder (
    @SerializedName("drinks")
    val drink: List<Drink>
)

interface RandomDrinkAPI {
    @get:GET("random.php")

    val drinks: Call <DrinkHolder>

}
