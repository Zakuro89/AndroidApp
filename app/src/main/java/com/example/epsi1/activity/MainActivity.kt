package com.example.epsi1.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.R
import com.example.epsi1.adapter.RecetteAdapter
import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.db.database.RecetteDatabase
import com.example.epsi1.model.RecetteEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewRecipe: RecyclerView
    private lateinit var recipeAdapter: RecetteAdapter
    private val recipesList = mutableListOf<RecetteEntity>()

    private lateinit var database: RecetteDatabase
    private lateinit var recetteDao: RecetteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = RecetteDatabase.getDatabase(this)
        recetteDao = database.recetteDao()

        recyclerViewRecipe = findViewById(R.id.recipesRecyclerView)
        recyclerViewRecipe.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val dividerItemDecoration = DividerItemDecoration(
            recyclerViewRecipe.context,
            (recyclerViewRecipe.layoutManager as LinearLayoutManager).orientation
        )

        recyclerViewRecipe.addItemDecoration(dividerItemDecoration)
        recipeAdapter = RecetteAdapter(this, recipesList)
        recyclerViewRecipe.adapter = recipeAdapter

        val addRecipeButton = findViewById<FloatingActionButton>(R.id.addRecipeButton)
        addRecipeButton.setOnClickListener {
            val intent = Intent(this, AjouterRecette::class.java)
            startActivity(intent)
        }



    }

    override fun onResume() {
        super.onResume()
        loadRecipes()
    }


    private fun loadRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            val recettes = recetteDao.getAllRecettes()

            withContext(Dispatchers.Main) {
                if (recettes.isNotEmpty()) {
                    findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardRecipeAll).visibility =
                        View.VISIBLE

                    findViewById<TextView>(R.id.noRecipeAlert).visibility = View.GONE

                } else {
                    findViewById<TextView>(R.id.noRecipeAlert).visibility = View.VISIBLE
                }

                recipesList.clear()
                recipesList.addAll(recettes)
                recipeAdapter.notifyDataSetChanged()
            }
        }
    }


}
