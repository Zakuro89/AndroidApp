package com.example.epsi1.adapter

import com.squareup.picasso.Picasso
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.epsi1.R
import com.example.epsi1.model.RecetteEntity
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.activity.RecetteDetails
import java.io.File


class RecetteAdapter(private val context: Context, private val recipesList: List<RecetteEntity>) :
    RecyclerView.Adapter<RecetteAdapter.RecetteViewHolder>() {


    class RecetteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val card: View = itemView
        val recetteTitle: TextView = itemView.findViewById(R.id.recipeTitle)
        val recetteImg: ImageView = itemView.findViewById(R.id.recipeImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recette_elements, parent, false)
        return RecetteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetteViewHolder, position: Int) {
        val recette = recipesList[position]
        holder.recetteTitle.text = recette.title
        holder.recetteImg.setImageResource(R.drawable.ic_cadre)

        val imgPath = recette.img

        if (imgPath != null) {
            if (imgPath.isNotEmpty()) {
                val file = File(imgPath)
                if (file.exists()) {
                    Picasso.get()
                        .load(file)
                        .placeholder(R.drawable.ic_waiting)
                        .error(R.drawable.ic_error)
                        .into(holder.recetteImg)
                } else {
                    holder.recetteImg.setImageResource(R.drawable.ic_edit)
                }
            } else {
                holder.recetteImg.setImageResource(R.drawable.ic_ingredient)
            }
        }

        holder.card.setOnClickListener {
            val intent = Intent(context, RecetteDetails::class.java)
            intent.putExtra("recette_id", recette.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }


}