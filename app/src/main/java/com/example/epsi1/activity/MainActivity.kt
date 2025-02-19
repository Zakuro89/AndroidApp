package com.example.epsi1.activity


import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.epsi1.R
import com.example.epsi1.adapter.RecetteAdapter
import com.example.epsi1.model.Recette
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var listViewRecipe: GridView
    private val recipesList = mutableListOf<Recette>(
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1"),
        Recette("titre 1")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addRecipeButton = findViewById<FloatingActionButton>(R.id.addRecipeButton)


        addRecipeButton.setOnClickListener {
            val intent = Intent(this, AjouterRecette::class.java)
            startActivityForResult(intent, 1)
        }

        listViewRecipe = findViewById(R.id.recipesListView)

        val recipeAdapter = RecetteAdapter(this, recipesList)
        listViewRecipe.adapter = recipeAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val title = data.getStringExtra("recipe_title") ?: return

            val newRecipe = Recette(title)
            recipesList.add(newRecipe)

            val recipeAdapter = RecetteAdapter(this, recipesList)
            listViewRecipe.adapter = recipeAdapter

        }
    }


}
