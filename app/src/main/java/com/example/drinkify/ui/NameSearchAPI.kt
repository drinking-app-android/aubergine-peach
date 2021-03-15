package com.example.drinkify.ui


import com.example.drinkify.model.Drink
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NameSearchAPI {/*
    @get:GET("search.php?s={name}")
     val drinks: Call<DrinkHolder> searchName(@Path(name, encoded = true) String name)
     fun searchName(@Path("name")name: String)

    @GET("{fullUrl}")
    fun searchName(@Path(value = "fullUrl", encoded = false) fullUrl: String?):  Call<List<Drink>>
*/
    @GET
    open fun list(@Url url: String?): Call<DrinkHolder>
}
