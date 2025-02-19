package com.example.epsi1.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.epsi1.R
import com.example.epsi1.adapter.EtapeAdapter
import com.example.epsi1.adapter.IngredientAdapter
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient

class AjouterRecette : AppCompatActivity() {

    private lateinit var listViewIngredient: ListView
    private lateinit var listViewStep: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ajouter_recettes)

        listViewIngredient = findViewById(R.id.ingredientListView)
        listViewStep = findViewById((R.id.stepListView))

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            finish()
        }

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

        val sauvegarderRecetteBouton = findViewById<Button>(R.id.saveRecipeButton)
        sauvegarderRecetteBouton.setOnClickListener {
            val title = findViewById<EditText>(R.id.recipeTitle).text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("recipe_title", title)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private val ingredientsList = mutableListOf<Ingredient>()
    private val stepsList = mutableListOf<Etape>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("ingredient_name") ?: return
            val quantity = data.getDoubleExtra("ingredient_quantity", 0.0)
            val unit = data.getStringExtra("ingredient_unit") ?: return


            val newIngredient = Ingredient(name, quantity, unit)
            ingredientsList.add(newIngredient)


            val ingredientAdapter = IngredientAdapter(this, ingredientsList)


            listViewIngredient.adapter = ingredientAdapter


        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val description = data.getStringExtra("step_description") ?: return

            val newStep = Etape(description)
            stepsList.add(newStep)

            val stepAdapter = EtapeAdapter(this, stepsList)
            listViewStep.adapter = stepAdapter

        }
    }


}

