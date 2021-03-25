package com.example.drinkify.model

import com.google.gson.annotations.SerializedName

data class Fav (

    @SerializedName("idDrink")
    val idDrink: String? = null,

    @SerializedName("strDrink")
    val strDrink: String? = null

)

