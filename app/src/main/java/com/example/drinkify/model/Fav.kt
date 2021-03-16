package com.example.drinkify.model

import com.google.gson.annotations.SerializedName

data class Fav (
    val id: Int,

    @SerializedName("idDrink")
    val idDrink: String? = null,

    @SerializedName("strDrink")
    val strDrink: String? = null
)

