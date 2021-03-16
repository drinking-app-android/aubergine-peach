package com.example.drinkify.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkify.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this@SearchableActivity, android.R.layout.simple_list_item_1)
        //Search

        var arraylist = ArrayList<Int>()
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query != null) {
                val searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/search.php?s=" + query
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://www.thecocktaildb.com/api/json/v2/9973533/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                val searchDrinkByName = retrofit.create(SearchAPI::class.java)
                searchDrinkByName.list(searchURL)?.enqueue(object : Callback<DrinkHolder> {
                    override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                        if (!response.isSuccessful) {
                            Log.d("Response errorBody", response.errorBody().toString())
                            return
                        }
                        if(response.body()?.drink.isNullOrEmpty()){
                            itemsAdapter.add("No drink found, did you spell wrong? It might be time for some water!")
                        }else{
                            val whatsInsideA = response.body()!!
                            for (drinkProperty in whatsInsideA.drink) {
                                itemsAdapter.add(drinkProperty.strDrink)
                                drinkProperty.idDrink?.toInt()?.let { arraylist.add(it) }

                            }
                        }
                    }
                    override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                        Log.d("Failure", "Crash bing boom on failure")
                    }
                })
            }
        }
        val searchListView = findViewById<ListView>(R.id.searchListView)
        searchListView.adapter = itemsAdapter
        searchListView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DrinkActivity::class.java)
            intent.putExtra("DrinkID", arraylist[id.toInt()])
            startActivity(intent)

        })

                /*
                searchFunction(query)
                That function should also display the
                search results in a listview where the
                user can click on the results to go to DrinkActivity
                of that drink!
                 */

    }
}







