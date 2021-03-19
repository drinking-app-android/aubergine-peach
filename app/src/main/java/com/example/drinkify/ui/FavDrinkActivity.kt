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
        //creating the instance of AppDatabase class
        val databaseHandler: AppDatabase = AppDatabase(this)
        //calling the viewFAV method of DatabaseHandler class to read the records
        val favList: ArrayList<Fav> = databaseHandler.viewFav()

        return favList
    }
    /**
     * Function is used to show the list on UI of inserted data.
     */
    private fun setupListOfDataIntoRecyclerView() {
        val rvItemsList : RecyclerView = findViewById(R.id.rvItemsList)
        val tvNoRecordsAvailable : TextView = findViewById(R.id.tvNoRecordsAvailable)
        if (getItemsList().size > 0) {

            rvItemsList.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility = View.GONE

            // Set the LayoutManager that this RecyclerView will use.
            rvItemsList.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val favItemAdapter = FavItemAdapter(this, getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.
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
    /**
     * Method is used to show the delete alert dialog.
     */
    fun deleteRecordAlertDialog(fav: Fav) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Record")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to delete ${fav.strDrink}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            //creating the instance of DatabaseHandler class
            val databaseHandler: AppDatabase = AppDatabase(this)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteEmployee(Fav(fav.idDrink, ""))
            if (status > -1) {
                Toast.makeText(
                        applicationContext,
                        "Record deleted successfully.",
                        Toast.LENGTH_LONG
                ).show()

                setupListOfDataIntoRecyclerView()
            }

            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }
}