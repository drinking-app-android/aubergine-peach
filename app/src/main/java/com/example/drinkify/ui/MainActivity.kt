package com.example.drinkify.ui

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.drinkify.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ranomizeActivityButton: Button = findViewById(R.id.randomizeButton)
        ranomizeActivityButton.setOnClickListener{
            val intent = Intent(this, RandomizeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        val generateActivityButton: Button = findViewById(R.id.generateButton)
        generateActivityButton.setOnClickListener{
         val intent = Intent(this, PreferenceActivity::class.java)
            startActivity(intent)
        }

        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener{
            onSearchRequested()
        }

    }


}