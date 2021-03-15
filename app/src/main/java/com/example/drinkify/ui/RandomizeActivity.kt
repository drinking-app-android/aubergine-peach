package com.example.drinkify.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.drinkify.R
import com.example.drinkify.model.Drink
import com.example.drinkify.model.Post
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class RandomizeActivity : AppCompatActivity() {
    private var textViewResult: TextView? = null
    private var textViewDrinkName: TextView? = null
    private var textViewIngredients: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomize)

        textViewDrinkName = findViewById(R.id.drink_name)
        textViewResult = findViewById(R.id.text_view_result)
        textViewIngredients = findViewById(R.id.ingredients_result)
        val drinkImage: ImageView = findViewById(R.id.drink_image)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val randomDrinkApi: RandomDrinkAPI = retrofit.create(RandomDrinkAPI::class.java)
        val call = randomDrinkApi.drinks
        call.enqueue(object: Callback<DrinkHolder> {
            override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                if (!response.isSuccessful) {
                    textViewResult?.setText("Code: " + response.code())
                    return
                }

                val whatsInsideA = response.body()!!
                //Log.d("drinkname", drink.drink[0].strDrink.toString())
                for (drinkProperty in whatsInsideA.drink){
                    var content = ""
                    content += """
                        Category: ${drinkProperty.strCategory}
                        
                        """.trimIndent()
                    content += """
                        Type: ${drinkProperty.strAlcoholic}
                        
                        """.trimIndent()
                    content += """
                        Alcoholic: ${drinkProperty.strGlass}
                        
                        """.trimIndent()
                    content += """
                        Instructions ${drinkProperty.strInstructions}
                        
                        
                        """.trimIndent()
                    var ingredientArray = arrayOf(drinkProperty.strIngredient1,
                        drinkProperty.strIngredient2, drinkProperty.strIngredient3,
                        drinkProperty.strIngredient4,drinkProperty.strIngredient5,
                        drinkProperty.strIngredient6,drinkProperty.strIngredient7,
                        drinkProperty.strIngredient8, drinkProperty.strIngredient9,
                        drinkProperty.strIngredient10,drinkProperty.strIngredient11,
                        drinkProperty.strIngredient12,drinkProperty.strIngredient13,
                        drinkProperty.strIngredient14, drinkProperty.strIngredient15)

                    var measureArray = arrayOf(drinkProperty.strMeasure1,
                        drinkProperty.strMeasure2, drinkProperty.strMeasure3,
                        drinkProperty.strMeasure4, drinkProperty.strMeasure5,
                        drinkProperty.strMeasure6, drinkProperty.strMeasure7,
                        drinkProperty.strMeasure8, drinkProperty.strMeasure9,
                        drinkProperty.strMeasure10, drinkProperty.strMeasure11,
                        drinkProperty.strMeasure12, drinkProperty.strMeasure13,
                        drinkProperty.strMeasure14, drinkProperty.strMeasure15)

                    textViewDrinkName?.append(drinkProperty.strDrink)
                    //textViewIngredients?.append(ingredientArray)
                    Glide.with(this@RandomizeActivity).load(drinkProperty.strDrinkThumb).into(drinkImage)
                    textViewResult?.append(content)

                }

            }

            override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                textViewResult?.setText(t.message)
            }
        })
    }
}
