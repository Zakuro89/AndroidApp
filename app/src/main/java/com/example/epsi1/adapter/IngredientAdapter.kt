package com.example.epsi1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.R
import com.example.epsi1.model.Ingredient

class IngredientAdapter(private val context: Context, private val ingredientsList: List<Ingredient>) : BaseAdapter() {

    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }



    override fun getCount(): Int = ingredientsList.size

    override fun getItem(position: Int): Any = ingredientsList[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class Holder {
        lateinit var name: TextView
        lateinit var quantity: TextView
        lateinit var unit: TextView
    }

    private fun initHolder(view: View): Holder {
        val holder = Holder()
        holder.name = view.findViewById(R.id.ingredientName)
        holder.quantity = view.findViewById(R.id.ingredientQuantity)
        holder.unit = view.findViewById(R.id.ingredientUnit)

        return holder
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        if (cv == null) {
            cv = inflater!!.inflate(R.layout.ingredient_elements, parent, false)
        }
        val holder = initHolder(cv!!)
        val ingredient = ingredientsList[position]

        holder.name.text = ingredient.nom
        holder.quantity.text = ingredient.quantite.toString()
        holder.unit.text = ingredient.unite

        return cv
    }

}