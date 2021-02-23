package com.example.drinkify.data

import android.util.Log
import java.net.URL

class Request(private val url: String){
    fun run(){
        val randomDrinkJson = URL(url).readText()
        Log.d("onpoint", randomDrinkJson)
    }
}