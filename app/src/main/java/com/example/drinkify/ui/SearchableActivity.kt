package com.example.drinkify.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkify.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchableActivity : AppCompatActivity() {
    private var searchSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        searchSpinner = findViewById(R.id.progressBarSearch)
        searchSpinner?.visibility = View.VISIBLE

        val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this@SearchableActivity, android.R.layout.simple_list_item_1)
        val searchListView = findViewById<ListView>(R.id.searchListView)
        val arraylist = ArrayList<Int>()
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query != null) {
                val searchURL = "https://www.thecocktaildb.com/api/json/v2/9973533/search.php?s=" + query
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://www.thecocktaildb.com/api/json/v2/9973533/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                val searchDrinkByName = retrofit.create(SearchAPI::class.java)
                searchDrinkByName.list(searchURL).enqueue(object : Callback<DrinkHolder> {

                    override fun onResponse(call: Call<DrinkHolder>, response: Response<DrinkHolder>) {
                        if (!response.isSuccessful) {
                            Toast.makeText(applicationContext, getString(R.string.APIError), Toast.LENGTH_LONG).show()
                            return
                        }
                        if (response.body()?.drink.isNullOrEmpty()) {
                            itemsAdapter.add(getString(R.string.searchNoDrinkFound))
                            searchListView.adapter = itemsAdapter
                            searchListView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
                                val intent = Intent(this@SearchableActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            })

                        } else {
                            searchSpinner?.visibility = View.GONE
                            val whatsInsideA = response.body()!!
                            for (drinkProperty in whatsInsideA.drink) {
                                itemsAdapter.add(drinkProperty.strDrink)
                                drinkProperty.idDrink?.toInt()?.let { arraylist.add(it) }

                            }
                            searchListView.adapter = itemsAdapter
                            searchListView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
                                val intent = Intent(this@SearchableActivity, DrinkActivity::class.java)
                                intent.putExtra("DrinkID", arraylist[id.toInt()])
                                startActivity(intent)
                                finish()
                            })
                        }
                    }

                    override fun onFailure(call: Call<DrinkHolder>, t: Throwable) {
                        Toast.makeText(
                                applicationContext,
                                getString(R.string.APIError),
                                Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }
}







