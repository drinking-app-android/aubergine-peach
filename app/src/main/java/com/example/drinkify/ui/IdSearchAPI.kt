package com.example.drinkify.ui

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IdSearchAPI {

    @GET
    open fun list(@Url url: String?): Call<DrinkHolder>
}