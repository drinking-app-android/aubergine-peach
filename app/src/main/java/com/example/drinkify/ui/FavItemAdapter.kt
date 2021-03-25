package com.example.drinkify.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkify.R
import com.example.drinkify.model.Fav
import org.jetbrains.anko.image

class FavItemAdapter(val context: Context, val items: ArrayList<Fav>) :
        RecyclerView.Adapter<FavItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_row,
                        parent,
                        false
                )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.get(position)

        holder.tvName.text = item.strDrink

        holder.tvName.setOnClickListener { view ->
            if (context is FavDrinkActivity){
                context.showDrinkClicked(item)
            }
        }

        holder.ivDelete.setOnClickListener { view ->

            if (context is FavDrinkActivity) {
                context.deleteRecordAlertDialog(item)
            }
        }
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                    ContextCompat.getColor(
                            context,
                            R.color.colorLightGray
                    )
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }
    }
    override fun getItemCount(): Int {
        return items.size

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val llMain: LinearLayout = view.findViewById(R.id.llMain)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val ivDelete:ImageView = view.findViewById(R.id.ivDelete)
    }
}