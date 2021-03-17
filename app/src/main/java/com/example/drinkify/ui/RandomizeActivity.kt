package com.example.drinkify.ui

import AppDatabase
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.drinkify.R
import com.example.drinkify.model.Fav
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RandomizeActivity : AppCompatActivity() {
    private var textViewResult: TextView? = null
    private var textViewDrinkName: TextView? = null
    private var textViewIngredients: TextView? = null
    private var textViewMeasurements: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomize)

        textViewDrinkName = findViewById(R.id.drink_name)
        textViewResult = findViewById(R.id.text_view_result)
        textViewIngredients = findViewById(R.id.ingredients_result)
        textViewMeasurements = findViewById(R.id.measurements_result)

        val newDrinkButton : Button = findViewById(R.id.newRandom)
        newDrinkButton.setOnClickListener{
            val intent = Intent(this, RandomizeActivity::class.java)
            startActivity(intent)
        }
        val drinkImage: ImageView = findViewById(R.id.drink_image)

        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }



        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v2/9973533/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val randomDrinkApi: RandomDrinkAPI = retrofit.create(RandomDrinkAPI::class.java)
        val call = randomDrinkApi.drinks
        call.enqueue(object : Callback<DrinkHolder> {
            override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                if (!response.isSuccessful) {
                    textViewResult?.setText("Code: " + response.code())
                    return
                }

                val whatsInsideA = response.body()!!
                //Log.d("drinkname", drink.drink[0].strDrink.toString())
                for (drinkProperty in whatsInsideA.drink) {
                    var content = ""
                    content += """
                        Category: ${drinkProperty.strCategory}
                        
                        """.trimIndent()
                    content += """
                        Type: ${drinkProperty.strAlcoholic}
                        
                        """.trimIndent()
                    content += """
                        Glass: ${drinkProperty.strGlass}
                        
                        """.trimIndent()
                    content += """
                        Instructions: ${drinkProperty.strInstructions}
                        
                        
                        """.trimIndent()
                    var ingredientArray = arrayOf(
                        drinkProperty.strIngredient1,
                        drinkProperty.strIngredient2, drinkProperty.strIngredient3,
                        drinkProperty.strIngredient4, drinkProperty.strIngredient5,
                        drinkProperty.strIngredient6, drinkProperty.strIngredient7,
                        drinkProperty.strIngredient8, drinkProperty.strIngredient9,
                        drinkProperty.strIngredient10, drinkProperty.strIngredient11,
                        drinkProperty.strIngredient12, drinkProperty.strIngredient13,
                        drinkProperty.strIngredient14, drinkProperty.strIngredient15
                    )

                    for (ingredient in ingredientArray) {
                        if (ingredient != null) {
                            textViewIngredients?.append("\n" + ingredient.toString())
                        }
                    }

                    var measureArray = arrayOf(
                        drinkProperty.strMeasure1,
                        drinkProperty.strMeasure2, drinkProperty.strMeasure3,
                        drinkProperty.strMeasure4, drinkProperty.strMeasure5,
                        drinkProperty.strMeasure6, drinkProperty.strMeasure7,
                        drinkProperty.strMeasure8, drinkProperty.strMeasure9,
                        drinkProperty.strMeasure10, drinkProperty.strMeasure11,
                        drinkProperty.strMeasure12, drinkProperty.strMeasure13,
                        drinkProperty.strMeasure14, drinkProperty.strMeasure15
                    )

                    for (measurement in measureArray) {
                        if (measurement != null) {
                            textViewMeasurements?.append("\n" + measurement.toString())
                        }
                    }

                    textViewDrinkName?.append(drinkProperty.strDrink)


                    Glide.with(this@RandomizeActivity).load(drinkProperty.strDrinkThumb).into(
                        drinkImage
                    )
                    textViewResult?.append(content)

                    val favButton: ImageButton = findViewById(R.id.favoritesButton)
                    favButton.setOnClickListener{
                        var isFavorite = readStae()
                        if (isFavorite){
                            favButton.setBackgroundResource(R.drawable.stardrawable_empty)
                            isFavorite = false
                            saveStae(isFavorite)
                        }else{
                            //TODO: Add to the database. It is maybe work yes, kan inte inspektera table dock. Vilket kanske betyder att det inte funkar..
                            favButton.setBackgroundResource(R.drawable.stardrawable)
                            isFavorite = true
                            drinkProperty.idDrink?.let { it1 -> drinkProperty.strDrink?.let { it2 ->
                                drinkProperty.strDrinkThumb?.let { it3 ->
                                    addToFavorites(it1,
                                        it2, it3
                                    )
                                }
                            } }
                            //drinkProperty.idDrink?.let { it1 -> drinkProperty.strDrink?.let { it2 -> addToFavorites(it1, it2) } }
                            saveStae(isFavorite)
                        }
                    }

                }

            }

            override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                textViewResult?.setText(t.message)
            }
        })
    }

    private fun readStae(): Boolean {
        val aSharedPreferenes = getSharedPreferences(
            "Favourite", Context.MODE_PRIVATE
        )
        return aSharedPreferenes.getBoolean("State", true)

    }

    private fun saveStae(isFavourite: Boolean) {
        val aSharedPreferenes = getSharedPreferences(
            "Favourite", Context.MODE_PRIVATE
        )
        val aSharedPreferenesEdit = aSharedPreferenes
            .edit()
        aSharedPreferenesEdit.putBoolean("State", isFavourite)
        aSharedPreferenesEdit.commit()
    }

    private fun addToFavorites (theDrinkId: String, theDrinkName: String, theDrinkImg: String){
        val drinkId : String = theDrinkId
        val drinkName: String = theDrinkName
        val drinkImg: String = theDrinkImg
        val appDatabase: AppDatabase = AppDatabase(this)
        if (!drinkId.isEmpty() && !drinkName.isEmpty()) {
            val status =
                    appDatabase.addFavDrink(Fav(0, drinkId, drinkName, drinkImg))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    "Error oh no uwu fucky wucky senpai",
                    Toast.LENGTH_LONG
            ).show()
        }
    }


}
