package com.example.epsi1.activity


import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.epsi1.R
import com.example.epsi1.adapter.RecetteAdapter
import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.db.database.RecetteDatabase
import com.example.epsi1.model.Recette
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var listViewRecipe: GridView
    private lateinit var recipeAdapter: RecetteAdapter
    private val recipesList = mutableListOf<Recette>()

    private lateinit var database: RecetteDatabase
    private lateinit var recetteDao: RecetteDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = RecetteDatabase.getDatabase(this)
        recetteDao = database.recetteDao()
        listViewRecipe = findViewById(R.id.recipesListView)

        val addRecipeButton = findViewById<FloatingActionButton>(R.id.addRecipeButton)

        // Initialisation de l'adapter
        recipeAdapter = RecetteAdapter(this, recipesList)
        listViewRecipe.adapter = recipeAdapter


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
                recipesList.clear()
                recipesList.addAll(recettes)
                recipeAdapter.notifyDataSetChanged()
            }
        }
    }


}
