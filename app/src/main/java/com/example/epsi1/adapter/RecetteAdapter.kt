package com.example.epsi1.adapter

import com.squareup.picasso.Picasso
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.epsi1.R
import com.example.epsi1.model.Recette
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class RecetteAdapter(private val context: Context, private val recipesList: List<Recette>) :
    RecyclerView.Adapter<RecetteAdapter.RecetteViewHolder>() {

    class RecetteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }


//    companion object {
//        private var inflater: LayoutInflater? = null
//    }
//
//    init {
//        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    }

//
//    override fun getCount(): Int = recipesList.size
//
//    override fun getItem(position: Int): Any = recipesList[position]
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    class Holder {
//        lateinit var title: TextView
//        lateinit var image: ImageView
//    }
//
//    private fun initHolder(view: View): Holder {
//        val holder = Holder()
//        holder.title = view.findViewById(R.id.recipeTitle)
//        holder.image = view.findViewById((R.id.recipeImg))
//
//
//        return holder
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var cv = convertView
//        if (cv == null) {
//            cv = inflater!!.inflate(R.layout.recette_elements, parent, false)
//        }
//        val holder = initHolder(cv!!)
//        val recipe = recipesList[position]
//
//        holder.title.text = recipe.title
//
//        val imgPath = recipe.img
//
//        if (imgPath != null) {
//            if (imgPath.isNotEmpty()) {
//                val file = File(imgPath)
//                if (file.exists()) {
//                    Picasso.get()
//                        .load(file)
//                        .placeholder(R.drawable.ic_waiting)
//                        .error(R.drawable.ic_error)
//                        .into(holder.image)
//                } else {
//                    holder.image.setImageResource(R.drawable.ic_edit)
//                }
//            } else {
//                holder.image.setImageResource(R.drawable.ic_ingredient)
//            }
//        }
//
//
//
//
//
//
//        return cv
//    }

}