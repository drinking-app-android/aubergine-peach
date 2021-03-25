package com.example.drinkify.ui

import AppDatabase
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkify.R
import com.example.drinkify.model.Fav

class FavDrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_drink)
        setupListOfDataIntoRecyclerView()


    }
    fun getItemsList(): ArrayList<Fav> {
        val databaseHandler: AppDatabase = AppDatabase(this)
        val favList: ArrayList<Fav> = databaseHandler.viewFav()

        return favList
    }
    private fun setupListOfDataIntoRecyclerView() {
        val rvItemsList : RecyclerView = findViewById(R.id.rvItemsList)
        val tvNoRecordsAvailable : TextView = findViewById(R.id.tvNoRecordsAvailable)
        if (getItemsList().size > 0) {

            rvItemsList.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility = View.GONE

            rvItemsList.layoutManager = LinearLayoutManager(this)
            val favItemAdapter = FavItemAdapter(this, getItemsList())
            rvItemsList.adapter = favItemAdapter
        } else {

            rvItemsList.visibility = View.GONE
            tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }

    fun showDrinkClicked(fav: Fav) {
        val intent = Intent(this, DrinkActivity::class.java)
        fav.idDrink?.let { intent.putExtra("DrinkID", it.toInt()) }
        startActivity(intent)
    }
    fun deleteRecordAlertDialog(fav: Fav) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.deleteRecord))
        builder.setMessage("${getString(R.string.validateDelete)} ${fav.strDrink}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->

            val databaseHandler: AppDatabase = AppDatabase(this)
            val status = databaseHandler.deleteFav(Fav(fav.idDrink, ""))
            if (status > -1) {
                Toast.makeText(
                        applicationContext,
                        "Record deleted successfully.",
                        Toast.LENGTH_LONG
                ).show()

                setupListOfDataIntoRecyclerView()
            }

            dialogInterface.dismiss()
        }

        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}