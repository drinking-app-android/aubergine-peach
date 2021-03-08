package com.example.drinkify.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkify.R
import com.example.drinkify.uhm.Drink
import com.example.drinkify.uhm.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class RandomizeActivity : AppCompatActivity() {
    private var textViewResult: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomize)

        textViewResult = findViewById(R.id.text_view_result)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val randomDrinkApi: RandomDrinkAPI = retrofit.create(RandomDrinkAPI::class.java)
        val call = randomDrinkApi.drinks
        call.enqueue(object: Callback<Drink> {
            override fun onResponse(call: Call<Drink>, response: Response<Drink>) {
                if (!response.isSuccessful) {
                    textViewResult?.setText("Code: " + response.code())
                    return
                }

                val drink = arrayListOf(response.body()!!)
                for (drinkProperty in drink) {
                    var content = ""
                    content += """
                        Drink ${drinkProperty.strDrink}
                        
                        """.trimIndent()
                    content += """
                        Category ${drinkProperty.strCategory.toString()}
                        
                        """.trimIndent()
                    content += """
                        Alcoholic ${drinkProperty.strAlcoholic.toString()}
                        
                        """.trimIndent()
                    content += """
                        Instructions ${drinkProperty.strInstructions.toString()}
                        
                        
                        """.trimIndent()
                    textViewResult?.append(content)

                }
            }

            override fun onFailure(call: Call<Drink>, t: Throwable) {
                textViewResult?.setText(t.message)
            }
        })
    }
}




/*
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkify.R
import com.example.drinkify.data.Request
import com.example.drinkify.uhm.Drink
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
}*/

/*
class RandomizeActivity : AppCompatActivity() {
    private var textViewResult: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomize)
        textViewResult = findViewById(R.id.text_view_result)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.posts
        call.enqueue(object : Callback<List<Post>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    textViewResult?.setText("Code: " + response.code())
                    return
                }
                val posts = response.body()!!
                for (post in posts) {
                    var content = ""
                    content += """
                        ID: ${post.id}
                        
                        """.trimIndent()
                    content += """
                        User ID: ${post.userId}
                        
                        """.trimIndent()
                    content += """
                        Title: ${post.title}
                        
                        """.trimIndent()
                    content += """
                        Text: ${post.text}
                        
                        
                        """.trimIndent()
                    textViewResult?.append(content)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                textViewResult?.setText(t.message)
            }
        })
    }
}
*/
