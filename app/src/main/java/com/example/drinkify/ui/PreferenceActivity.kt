package com.example.drinkify.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkify.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import org.jetbrains.anko.db.NULL
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        var userChoices = arrayListOf<String>("", "", "", "", "")
        val fruitArray = arrayListOf<String>("banana", "apple", "kiwi", "orange", "watermelon", "mango")

        val seekBar = findViewById<SeekBar>(R.id.seekBarBeer)
        val textView = findViewById<TextView>(R.id.textViewLevel)
        val vodkaButton = findViewById<Button>(R.id.vodkaButton)

        val rumButton = findViewById<Button>(R.id.rumButton)

        val tequilaButton = findViewById<Button>(R.id.tequilaButton)

        val whiskeyButton = findViewById<Button>(R.id.whiskeyButton)

        val sweetButton = findViewById<Button>(R.id.sweetButton)

        val sourButton = findViewById<Button>(R.id.sourButton)

        val bitterButton = findViewById<Button>(R.id.bitterButton)

        val saltButton = findViewById<Button>(R.id.saltButton)

        val creamyButton = findViewById<Button>(R.id.creamyButton)

        val fruityButton = findViewById<Button>(R.id.fruityButton)

        val veganButton = findViewById<Button>(R.id.veganButton)
        veganButton.setOnClickListener {
            veganButton.setBackgroundColor(Color.CYAN)
        }
        val savePreferenceButton = findViewById<Button>(R.id.savePrefButton)
        savePreferenceButton.setOnClickListener {
            TODO("Fixing fundamentals first")
        }
        vodkaButton.setOnClickListener {
            userChoices[0] = "vodka"
            vodkaButton.setBackgroundColor(Color.CYAN)
            rumButton.setBackgroundColor(Color.DKGRAY)
            tequilaButton.setBackgroundColor(Color.DKGRAY)
            whiskeyButton.setBackgroundColor(Color.DKGRAY)
        }
        rumButton.setOnClickListener {
            userChoices[0] = "rum"

            rumButton.setBackgroundColor(Color.CYAN)
            vodkaButton.setBackgroundColor(Color.DKGRAY)
            tequilaButton.setBackgroundColor(Color.DKGRAY)
            whiskeyButton.setBackgroundColor(Color.DKGRAY)
        }
        tequilaButton.setOnClickListener {
            userChoices[0] = "tequila"

            tequilaButton.setBackgroundColor(Color.CYAN)
            rumButton.setBackgroundColor(Color.DKGRAY)
            vodkaButton.setBackgroundColor(Color.DKGRAY)
            whiskeyButton.setBackgroundColor(Color.DKGRAY)
        }
        whiskeyButton.setOnClickListener {
            userChoices[0] = "whiskey"
            whiskeyButton.setBackgroundColor(Color.CYAN)
            vodkaButton.setBackgroundColor(Color.DKGRAY)
            rumButton.setBackgroundColor(Color.DKGRAY)
            tequilaButton.setBackgroundColor(Color.DKGRAY)
        }

        sweetButton.setOnClickListener {
            userChoices[1] = fruitArray.random()
            sweetButton.setBackgroundColor(Color.CYAN)
            sourButton.setBackgroundColor(Color.DKGRAY)
            bitterButton.setBackgroundColor(Color.DKGRAY)
            saltButton.setBackgroundColor(Color.DKGRAY)
        }
        sourButton.setOnClickListener {
            userChoices[1] = "lemon"
            sourButton.setBackgroundColor(Color.CYAN)
            sweetButton.setBackgroundColor(Color.DKGRAY)
            bitterButton.setBackgroundColor(Color.DKGRAY)
            saltButton.setBackgroundColor(Color.DKGRAY)
        }
        bitterButton.setOnClickListener {
            userChoices[1] = "vermouth" //Maybe something else thats bitter
            bitterButton.setBackgroundColor(Color.CYAN)
            sweetButton.setBackgroundColor(Color.DKGRAY)
            sourButton.setBackgroundColor(Color.DKGRAY)
            saltButton.setBackgroundColor(Color.DKGRAY)
        }
        saltButton.setOnClickListener {
            userChoices[1] = "salt"
            saltButton.setBackgroundColor(Color.CYAN)
            sourButton.setBackgroundColor(Color.DKGRAY)
            sweetButton.setBackgroundColor(Color.DKGRAY)
            bitterButton.setBackgroundColor(Color.DKGRAY)
        }

        creamyButton.setOnClickListener {
            userChoices[2] = "milk"
            creamyButton.setBackgroundColor(Color.CYAN)
            fruityButton.setBackgroundColor(Color.DKGRAY)
        }
        fruityButton.setOnClickListener {
            userChoices[2] = fruitArray.random()
            fruityButton.setBackgroundColor(Color.CYAN)
            creamyButton.setBackgroundColor(Color.DKGRAY)
        }

        val generateButton = findViewById<Button>(R.id.generatePrefButton)
        generateButton.setOnClickListener {
            if (userChoices[0] == "" && userChoices[1] == "" && userChoices[2] == "" && userChoices[3] == "" && userChoices[4] == ""){
                val snackbar = Snackbar.make(
                        findViewById(R.id.prefScrollView),
                        "You need to atleast one preferences!",
                        Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val searchResultList: MutableList<String> = ArrayList()
                var searchURL = ""
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://www.thecocktaildb.com/api/json/v2/9973533/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                if (userChoices[3] == "non-alcoholic") {
                    searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Non_Alcoholic"
                } else if (userChoices[4] == "shot") {
                    searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?c=Shot"
                } else {
                    if (!userChoices[2].isNullOrEmpty() && !userChoices[1].isNullOrEmpty() && !userChoices[0].isNullOrEmpty()) {
                        searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?i=" + userChoices[0] + "," + userChoices[1] + "," + userChoices[2]
                    } else if (!userChoices[1].isNullOrEmpty() && userChoices[0].isNullOrEmpty() && userChoices[2].isNullOrEmpty()) {
                        searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?i=" + userChoices[1]
                    } else if (userChoices[2].isNullOrEmpty() && userChoices[1].isNullOrEmpty()) {
                        searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?i=" + userChoices[0]
                    } else {
                        searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?i=" + userChoices[0] + "," + userChoices[1]
                    }
                }
                val searchDrinkByIngredient = retrofit.create(SearchAPI::class.java)
                searchDrinkByIngredient.list(searchURL)?.enqueue(object : Callback<DrinkHolder> {
                    override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                        if (!response.isSuccessful) {
                            Log.d("Response errorBody", response.errorBody().toString())
                            return
                        }
                        val whatsInsideA = response.body()!!
                        for (drinkProperty in whatsInsideA.drink) {
                            searchResultList.add(drinkProperty.idDrink.toString())
                        }
                        if (searchResultList.isNotEmpty()) {
                            if (searchResultList.random().toInt() != null) {
                                val drinkIDgenerated = searchResultList.random().toInt()
                                val intent = Intent(this@PreferenceActivity, DrinkActivity::class.java)
                                intent.putExtra("DrinkID", drinkIDgenerated)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                        val waitTime = 1500
                        val snackbar = Snackbar.make(
                                findViewById(R.id.prefScrollView),
                                "No drink found",
                                Snackbar.LENGTH_SHORT
                        ).show()
                        doAsync {
                            this@PreferenceActivity

                            Thread.sleep(waitTime.toLong())
                            startActivity(Intent(this@PreferenceActivity, PreferenceActivity::class.java))
                        }


                    }

                })

            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0 && progress <= 25) {
                    textView.setText("None")
                    userChoices[3] = "non-alcoholic"
                } else if (progress > 25 && progress <= 50) {
                    textView.setText("Little")
                } else if (progress > 50 && progress <= 75) {
                    textView.setText("Very")
                } else if (progress > 75 && progress <= 100) {
                    textView.setText("Mucho")
                    userChoices[4] = "shot"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext, "Start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext, "Stop tracking", Toast.LENGTH_SHORT).show()
            }
        })


    }

}


