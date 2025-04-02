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


}