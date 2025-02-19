package com.example.epsi1.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.epsi1.R
import com.example.epsi1.adapter.EtapeAdapter
import com.example.epsi1.adapter.IngredientAdapter
import com.example.epsi1.db.database.RecetteDatabase
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.Recette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AjouterRecette : AppCompatActivity() {

    private lateinit var listViewIngredient: ListView
    private lateinit var listViewStep: ListView
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var stepAdapter: EtapeAdapter

    private val ingredientsList = mutableListOf<Ingredient>()
    private val stepsList = mutableListOf<Etape>()

    private val database by lazy { RecetteDatabase.getDatabase(this) }
    private val recetteDao by lazy { database.recetteDao() }
    private val ingredientDao by lazy { database.ingredientDao() }
    private val etapeDao by lazy { database.etapeDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_recettes)

        listViewIngredient = findViewById(R.id.ingredientListView)
        listViewStep = findViewById((R.id.stepListView))

        ingredientAdapter = IngredientAdapter(this, ingredientsList)
        stepAdapter = EtapeAdapter(this, stepsList)

        listViewIngredient.adapter = ingredientAdapter
        listViewStep.adapter = stepAdapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ajouterIngredientBouton = findViewById<Button>(R.id.addIngredientButton)
        ajouterIngredientBouton.setOnClickListener {
            val intent = Intent(this, AjouterIngredient::class.java)
            startActivityForResult(intent, 1)
        }

        val ajouterEtapeBouton = findViewById<Button>(R.id.addStep)
        ajouterEtapeBouton.setOnClickListener {
            val intent = Intent(this, AjouterEtape::class.java)
            startActivityForResult(intent, 2)
        }

//        val sauvegarderRecetteBouton = findViewById<Button>(R.id.saveRecipeButton)
//        sauvegarderRecetteBouton.setOnClickListener {
//            val title = findViewById<EditText>(R.id.recipeTitle).text.toString()
//            val resultIntent = Intent()
//            resultIntent.putExtra("recipe_title", title)
//            setResult(Activity.RESULT_OK, resultIntent)
//            finish()
//        }

        val sauvegarderRecetteBouton = findViewById<Button>(R.id.saveRecipeButton)
        sauvegarderRecetteBouton.setOnClickListener {
            val title = findViewById<EditText>(R.id.recipeTitle).text.toString()
            saveRecipeToDatabase(title)
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

//    private val ingredientsList = mutableListOf<Ingredient>()
//    private val stepsList = mutableListOf<Etape>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("ingredient_name") ?: return
            val quantity = data.getDoubleExtra("ingredient_quantity", 0.0)
            val unit = data.getStringExtra("ingredient_unit") ?: return


            val newIngredient = Ingredient(nom = name, quantite = quantity, unite = unit)
            ingredientsList.add(newIngredient)
            ingredientAdapter.notifyDataSetChanged()




        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val description = data.getStringExtra("step_description") ?: return
            val newStep = Etape(description = description)

            stepsList.add(newStep)
            stepAdapter.notifyDataSetChanged()


        }
    }

    private fun saveRecipeToDatabase(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val recetteId = recetteDao.insertRecette(Recette(title = title))

            ingredientsList.forEach {
                it.recetteId = recetteId.toInt()
                ingredientDao.insertIngredient(it)
            }

            stepsList.forEach {
                it.recetteId = recetteId.toInt()
                etapeDao.insertEtape(it)
            }

            withContext(Dispatchers.Main) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

}

