package com.example.epsi1.adapter

import android.content.Context
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.R
import com.example.epsi1.model.Ingredient
import org.w3c.dom.Text

class IngredientAdapter(
    private val context: Context,
    private val ingredientsList: List<Ingredient>
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ingredient = itemView
        val ingredientName: TextView = itemView.findViewById(R.id.ingredientName)
        val ingredientQuantity: TextView = itemView.findViewById(R.id.ingredientQuantity)
        val ingredientUnit: TextView = itemView.findViewById((R.id.ingredientUnit))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ingredient_elements, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.ingredientName.text = ingredient.nom
        holder.ingredientQuantity.text = ingredient.quantite.toString()
        holder.ingredientUnit.text = ingredient.unite
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }


//    companion object {
//        private var inflater: LayoutInflater? = null
//    }
//
//    init {
//        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    }
//
//    override fun getCount(): Int = ingredientsList.size
//
//    override fun getItem(position: Int): Any = ingredientsList[position]
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    class Holder {
//        lateinit var name: TextView
//        lateinit var quantity: TextView
//        lateinit var unit: TextView
//    }
//
//    private fun initHolder(view: View): Holder {
//        val holder = Holder()
//        holder.name = view.findViewById(R.id.ingredientName)
//        holder.quantity = view.findViewById(R.id.ingredientQuantity)
//        holder.unit = view.findViewById(R.id.ingredientUnit)
//
//        return holder
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var cv = convertView
//        if (cv == null) {
//            cv = inflater!!.inflate(R.layout.ingredient_elements, parent, false)
//        }
//        val holder = initHolder(cv!!)
//        val ingredient = ingredientsList[position]
//
//        holder.name.text = ingredient.nom
//        holder.quantity.text = ingredient.quantite.toString()
//        holder.unit.text = ingredient.unite
//
//        return cv
//    }

}