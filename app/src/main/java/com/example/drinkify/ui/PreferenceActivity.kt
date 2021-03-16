package com.example.drinkify.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.drinkify.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        var userChoices = ""
        val fruitArray = arrayListOf<String>("banana", "apple", "kiwi", "orange", "watermelon", "mango")

        val seekBar = findViewById<SeekBar>(R.id.seekBarBeer)
        val textView = findViewById<TextView>(R.id.textViewLevel)
        val vodkaButton = findViewById<Button>(R.id.vodkaButton)
        vodkaButton.setOnClickListener{
            userChoices = "vodka"
            vodkaButton.setBackgroundColor(Color.CYAN)
        }
        val rumButton = findViewById<Button>(R.id.rumButton)
        rumButton.setOnClickListener{
            userChoices = "rum"
            rumButton.setBackgroundColor(Color.CYAN)
        }
        val tequilaButton = findViewById<Button>(R.id.tequilaButton)
        tequilaButton.setOnClickListener{
            userChoices = "tequila"
            tequilaButton.setBackgroundColor(Color.CYAN)
        }
        val whiskeyButton = findViewById<Button>(R.id.whiskeyButton)
        whiskeyButton.setOnClickListener{
            userChoices = "whiskey"
            whiskeyButton.setBackgroundColor(Color.CYAN)
        }
        val sweetButton = findViewById<Button>(R.id.sweetButton)
        sweetButton.setOnClickListener{
            userChoices = fruitArray.random()
            sweetButton.setBackgroundColor(Color.CYAN)
        }
        val sourButton = findViewById<Button>(R.id.sweetButton)
        sourButton.setOnClickListener{
            userChoices = "lemon"
            sourButton.setBackgroundColor(Color.CYAN)
        }
        val bitterButton = findViewById<Button>(R.id.bitterButton)
        bitterButton.setOnClickListener{
            userChoices = "vermouth" //Maybe something else thats bitter
            bitterButton.setBackgroundColor(Color.CYAN)
        }
        val saltButton = findViewById<Button>(R.id.saltButton)
        saltButton.setOnClickListener{
            userChoices = "salt"
            saltButton.setBackgroundColor(Color.CYAN)
        }
        val creamyButton = findViewById<Button>(R.id.creamyButton)
        creamyButton.setOnClickListener{
            userChoices = "milk"
            creamyButton.setBackgroundColor(Color.CYAN)
        }
        val fruityButton = findViewById<Button>(R.id.fruityButton)
        fruityButton.setOnClickListener{
            userChoices = fruitArray.random()
        }
        val veganButton = findViewById<Button>(R.id.veganButton)
        veganButton.setOnClickListener{
            TODO("not sure how to implement this")
        }
        val savePreferenceButton = findViewById<Button>(R.id.savePrefButton)
        savePreferenceButton.setOnClickListener{
            TODO("Fixing fundamentals first")
        }
        val generateButton = findViewById<Button>(R.id.generatePrefButton)
        generateButton.setOnClickListener{
            val drinkIDgenerated = generateDrink(userChoices)
            val intent = Intent(this, DrinkActivity::class.java)
            intent.putExtra("DrinkID", drinkIDgenerated)
            startActivity(intent)
        }


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress>0 && progress<=25)
                {
                    textView.setText("None")
                    userChoices = "non-alcoholic"
                }
                else if (progress>25 && progress<=50)
                {
                    textView.setText("Little")
                }
                else if(progress>50 && progress<=75)
                {
                    textView.setText("Very")
                }
                else if (progress>75 && progress<=100)
                {
                    textView.setText("Mucho")
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
    private fun generateDrink(userChoices: String): Int {
        var searchResultArray = arrayListOf<Int>()

        val searchURL = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + userChoices
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        val searchDrinkByIngredient = retrofit.create(SearchAPI::class.java)
        searchDrinkByIngredient.list(searchURL)?.enqueue(object : Callback<DrinkHolder> {
            //CRASHES ON THE ROW BELOW
            override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                if (!response.isSuccessful) {
                    Log.d("Response errorBody", response.errorBody().toString())
                    return
                }
                    val whatsInsideA = response.body()!!
                    for (drinkProperty in whatsInsideA.drink) {
                        drinkProperty.idDrink?.toInt()?.let { searchResultArray.add(it) }
                        drinkProperty.idDrink?.let { Log.d("drinkID generated", it) }
                }
            }
            override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                Log.d("Failure", "Crash bing boom on failure")
            }

        })
        return searchResultArray.random()
    }
}


