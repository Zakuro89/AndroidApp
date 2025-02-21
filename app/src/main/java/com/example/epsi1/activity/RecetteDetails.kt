package com.example.epsi1.activity

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.R
import com.example.epsi1.adapter.EtapeAdapter
import com.example.epsi1.adapter.IngredientAdapter
import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.db.database.RecetteDatabase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class RecetteDetails : AppCompatActivity() {

    private lateinit var recetteDao: RecetteDao
    private var recetteId: Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recette_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recetteDao = RecetteDatabase.getDatabase(this).recetteDao()
        recetteId = intent.getLongExtra("recette_id", -1L)

        if (recetteId == -1L) {
            Toast.makeText(this, "une erreur est survenue", Toast.LENGTH_SHORT).show()
            finish()
        }

        loadRecipe()

    }

    private fun loadRecipe() {
        CoroutineScope(Dispatchers.IO).launch {
            val myRecipe = recetteDao.getRecetteComplete(recetteId) ?: return@launch


            val imgPath = myRecipe.img

            withContext(Dispatchers.Main) {


                val title = findViewById<TextView>(R.id.my_recipe_title)

                title.text = myRecipe.title

                val ingredientListView = findViewById<RecyclerView>(R.id.my_recipe_ingredients)

                ingredientListView.layoutManager =
                    LinearLayoutManager(this@RecetteDetails, LinearLayoutManager.VERTICAL, false)

                val ingredientAdapter = IngredientAdapter(this@RecetteDetails, myRecipe.ingredients)
                ingredientListView.adapter = ingredientAdapter


                val stepsListView = findViewById<RecyclerView>(R.id.my_recipe_steps)

                stepsListView.layoutManager =
                    LinearLayoutManager(this@RecetteDetails, LinearLayoutManager.VERTICAL, false)

                val stepAdapter = EtapeAdapter(this@RecetteDetails, myRecipe.etapes)
                stepsListView.adapter = stepAdapter

                val imgView = findViewById<ImageView>(R.id.my_recipe_img)
                if (imgPath != null) {
                    if (imgPath.isNotEmpty()) {
                        val file = File(imgPath)
                        if (file.exists()) {
                            Picasso.get()
                                .load(file)
                                .placeholder(R.drawable.ic_waiting)
                                .error(R.drawable.ic_error)
                                .into(imgView)
                        } else {
                            imgView.setImageResource(R.drawable.ic_edit)
                        }
                    } else {
                        imgView.setImageResource(R.drawable.ic_ingredient)
                    }
                }


            }


        }
    }

    private fun deleteRecipe() {}
}