package com.example.drinkify.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.drinkify.R
import com.example.drinkify.data.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class RandomizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomize)

        val url = "https://www.thecocktaildb.com/api/json/v1/1/random.php"

        doAsync {
            Request(url).run()
            uiThread { toast("Request performed") }
        }

    }
}