package com.example.drinkify.ui

import AppDatabase
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.drinkify.R
import com.example.drinkify.model.Fav
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DrinkActivity : AppCompatActivity() {

    private var textViewResult: TextView? = null
    private var textViewDrinkName: TextView? = null
    private var textViewIngredients: TextView? = null
    private var textViewMeasurements: TextView? = null
    private var spinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)
        spinner = findViewById(R.id.progressBar)

        spinner?.visibility = View.VISIBLE

        textViewDrinkName = findViewById(R.id.drink_name)
        textViewResult = findViewById(R.id.text_view_result)
        textViewIngredients = findViewById(R.id.ingredients_result)
        textViewMeasurements = findViewById(R.id.measurements_result)
        val drinkImage: ImageView = findViewById(R.id.drink_image)
        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        val favButton: ImageButton = findViewById(R.id.favoritesButton)

        val databaseHandler: AppDatabase = AppDatabase(this)
        val favList = databaseHandler.viewFav()

        val idDrinkClicked = intent.getIntExtra("DrinkID", 0)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        val searchDrinkById = retrofit.create(SearchAPI::class.java)
        val searchURL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + idDrinkClicked
        searchDrinkById.list(searchURL)?.enqueue(object : Callback<DrinkHolder> {
            override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                if (!response.isSuccessful) {
                    Toast.makeText(applicationContext, getString(R.string.APIError), Toast.LENGTH_LONG).show()
                    return
                }

                spinner?.visibility = View.GONE
                val whatsInsideA = response.body()!!
                for (drinkProperty in whatsInsideA.drink) {
                    var content = ""
                    content += """
                        ${getString(R.string.category)} ${drinkProperty.strCategory}
                        
                        """.trimIndent()
                    content += """
                        ${getString(R.string.type)} ${drinkProperty.strAlcoholic}
                        
                        """.trimIndent()
                    content += """
                        ${getString(R.string.glass)} ${drinkProperty.strGlass}
                        
                        """.trimIndent()
                    content += """
                        ${getString(R.string.instructions)} ${drinkProperty.strInstructions}
                        
                        
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


                    Glide.with(this@DrinkActivity).load(drinkProperty.strDrinkThumb).into(
                            drinkImage
                    )
                    textViewResult?.append(content)
                    if (favList.contains(Fav(idDrinkClicked.toString(), drinkProperty.strDrink))) {
                        favButton.setBackgroundResource(R.drawable.stardrawable)
                        saveState(true)
                    }
                    favButton.setOnClickListener {
                        var isFavorite = readState()


                        if (isFavorite) {
                            favButton.setBackgroundResource(R.drawable.stardrawable_empty)
                            isFavorite = false
                            saveState(isFavorite)

                        } else {
                            favButton.setBackgroundResource(R.drawable.stardrawable)
                            isFavorite = true
                            drinkProperty.idDrink?.let { it1 ->
                                drinkProperty.strDrink?.let { it2 ->
                                    addToFavorites(it1,
                                            it2
                                    )

                                }
                            }
                            saveState(isFavorite)
                        }

                    }
                }
            }


            override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                Toast.makeText(applicationContext, getString(R.string.APIError), Toast.LENGTH_LONG).show()
            }
        })
    }

    fun addToFavorites(theDrinkId: String, theDrinkName: String) {
        val appDatabase: AppDatabase = AppDatabase(this)
        if (!theDrinkId.isEmpty() && !theDrinkName.isEmpty()) {
            val status =
                    appDatabase.addFavDrink(Fav(theDrinkId, theDrinkName))
            if (status > -1) {
                Toast.makeText(applicationContext, getString(R.string.recordSaved), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                        applicationContext,
                        getString(R.string.favoriteErrorMessage),
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun readState(): Boolean {
        val aSharedPreferenes = getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE
        )
        return aSharedPreferenes.getBoolean("State", true)

    }

    private fun saveState(isFavourite: Boolean) {
        val aSharedPreferenes = getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE
        )
        val aSharedPreferenesEdit = aSharedPreferenes
                .edit()
        aSharedPreferenesEdit.putBoolean("State", isFavourite)
        aSharedPreferenesEdit.commit()
    }
}

