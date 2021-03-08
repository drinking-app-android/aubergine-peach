package com.example.drinkify.ui

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.drinkify.R
import com.example.drinkify.uhm.Drink
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        //Search
        if(Intent.ACTION_SEARCH == intent.action){
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                search(query)
                /*
                searchFunction(query)
                That function should also display the
                search results in a listview where the
                user can click on the results to go to DrinkActivity
                of that drink!
                 */

            }
        }
    }

    fun search(query:String){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val searchDrinkByName = retrofit.create(NameSearchAPI::class.java)
        searchDrinkByName.searchName(query)
        val drinkArray = searchDrinkByName.drinks

        val searchList: MutableList<String> = ArrayList()

        val searchedArray = arrayOf(String())
        drinkArray.enqueue(object: Callback<Drink>{
            override fun onResponse(call: Call<Drink>, response: Response<Drink>) {
                if (response.code() == 200){
                    val drinkResponse = response.body()!!
                    val string = "Drink: " + drinkResponse.strDrink
                    searchList.add(string)

                    toast(string)
                    val searchListView: ListView = findViewById(R.id.searchListView)/*
                    searchListView.adapter = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        searchList
                        //All the drinks as a list/array
                    )*/
                }
            }

            override fun onFailure(call: Call<Drink>, t: Throwable) {
                    //Implement error handling
            }
        })
    }

}