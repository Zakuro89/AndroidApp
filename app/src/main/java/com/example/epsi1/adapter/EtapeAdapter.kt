package com.example.epsi1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.R
import com.example.epsi1.adapter.IngredientAdapter.IngredientViewHolder
import com.example.epsi1.model.Etape


class EtapeAdapter(private val context: Context, private val etapesList: List<Etape>) :
    RecyclerView.Adapter<EtapeAdapter.EtapeViewHolder>() {

    class EtapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val step = itemView
        val stepDescription: TextView = itemView.findViewById(R.id.stepDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtapeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.step_elements, parent, false)
        return EtapeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EtapeViewHolder, position: Int) {
        val step = etapesList[position]
        holder.stepDescription.text = "${position+1}. ${step.description}"

    }

    override fun getItemCount(): Int {
        return etapesList.size
    }

//    companion object {
//        private var inflater: LayoutInflater? = null
//    }

//    init {
//        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    }


//    override fun getCount(): Int = etapesList.size
//
//    override fun getItem(position: Int): Any = etapesList[position]
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }

//    class Holder {
//        lateinit var description: TextView
//
//    }

//    private fun initHolder(view: View): Holder {
//        val holder = Holder()
//        holder.description = view.findViewById(R.id.stepDescription)
//
//
//        return holder
//    }

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var cv = convertView
//        if (cv == null) {
//            cv = inflater!!.inflate(R.layout.step_elements, parent, false)
//        }
//        val holder = initHolder(cv!!)
//        val step = etapesList[position]
//
//        holder.description.text = "${position+1}. ${step.description}"
//
//
//        return cv
//    }

}