package com.example.epsi1.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.epsi1.R
import com.example.epsi1.model.Recette

class RecetteAdapter(private val context: Context, private val recipesList: List<Recette>) : BaseAdapter() {

    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }



    override fun getCount(): Int = recipesList.size

    override fun getItem(position: Int): Any = recipesList[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class Holder {
        lateinit var title: TextView

    }

    private fun initHolder(view: View): Holder {
        val holder = Holder()
        holder.title = view.findViewById(R.id.recipeTitle)


        return holder
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        if (cv == null) {
            cv = inflater!!.inflate(R.layout.recette_elements, parent, false)
        }
        val holder = initHolder(cv!!)
        val recipe = recipesList[position]

        holder.title.text = recipe.title


        return cv
    }

}