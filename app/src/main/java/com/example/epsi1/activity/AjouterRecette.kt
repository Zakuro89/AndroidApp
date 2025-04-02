package com.example.epsi1.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.epsi1.R
import com.example.epsi1.adapter.EtapeAdapter
import com.example.epsi1.adapter.IngredientAdapter
import com.example.epsi1.db.dao.EtapeDao
import com.example.epsi1.db.dao.IngredientDao
import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.db.database.RecetteDatabase
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.RecetteEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AjouterRecette : AppCompatActivity() {

    private val newRecipe = RecetteEntity(title = "", img = null, pieces = null)

    private lateinit var listViewIngredient: RecyclerView
    private lateinit var listViewStep: RecyclerView

    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var stepAdapter: EtapeAdapter

    private val ingredientsList = mutableListOf<Ingredient>()
    private val stepsList = mutableListOf<Etape>()

    private lateinit var database: RecetteDatabase
    private lateinit var recetteDao: RecetteDao
    private lateinit var ingredientDao: IngredientDao
    private lateinit var etapeDao: EtapeDao

    private val ADD_INGREDIENT_REQUEST_CODE = 1
    private val ADD_STEP_REQUEST_CODE = 2
    private val CAMERA_REQUEST_CODE = 3
    private val GALLERY_REQUEST_CODE = 4

    private lateinit var imageUri: Uri
    private lateinit var imagePreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_recettes)


        database = RecetteDatabase.getDatabase(this)
        recetteDao = database.recetteDao()
        ingredientDao = database.ingredientDao()
        etapeDao = database.etapeDao()

        // on initialise la liste
        listViewIngredient = findViewById(R.id.ingredientListView)

        // on précise à la liste le sens dans lequel elle est ordonnée
        listViewIngredient.layoutManager =
            LinearLayoutManager(this@AjouterRecette, LinearLayoutManager.VERTICAL, false)

        listViewStep = findViewById(R.id.stepListView)
        listViewStep.layoutManager =
            LinearLayoutManager(this@AjouterRecette, LinearLayoutManager.VERTICAL, false)

        ingredientAdapter = IngredientAdapter(this, ingredientsList)
        stepAdapter = EtapeAdapter(this, stepsList)

        listViewIngredient.adapter = ingredientAdapter
        listViewStep.adapter = stepAdapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imagePreview = findViewById(R.id.imagePreview)

        val fabMain = findViewById<FloatingActionButton>(R.id.fabMain)
        fabMain.setOnClickListener {
            showBottomSheet()
        }

        val sauvegarderRecetteBouton = findViewById<FloatingActionButton>(R.id.saveRecipeButton)

        // ajout d'un écouteur d'évènements pour sauvegarder la recette
        sauvegarderRecetteBouton.setOnClickListener {
            val title = findViewById<EditText>(R.id.recipeTitle).text.toString()
            var nbPieces = findViewById<EditText>(R.id.recipePieces).text.toString().toIntOrNull()
            if (title.isNotEmpty()) {
                saveRecipeToDatabase(title, nbPieces)

            } else {
                Toast.makeText(this, "Veuillez indiquer un nom de recette.", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // affichage d'un feuillet via le boutton flottant pour accèder à différentes actions
    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        val ajouterIngredientBouton =
            view.findViewById<FloatingActionButton>(R.id.addIngredientButton)
        val ajouterEtapeBouton = view.findViewById<FloatingActionButton>(R.id.addStep)
        val takePhotoButton = view.findViewById<FloatingActionButton>(R.id.takePhotoButton)
        val chooseFromGalleryButton =
            view.findViewById<FloatingActionButton>(R.id.chooseFromGalleryButton)

        val cancelButton = view.findViewById<FloatingActionButton>(R.id.cancelButton)

        ajouterIngredientBouton.setOnClickListener {
            val intent = Intent(this, AjouterIngredient::class.java)
            startActivityForResult(intent, ADD_INGREDIENT_REQUEST_CODE)
        }

        ajouterEtapeBouton.setOnClickListener {
            val intent = Intent(this, AjouterEtape::class.java)
            startActivityForResult(intent, ADD_STEP_REQUEST_CODE)
        }

        takePhotoButton.setOnClickListener {
            openCamera()
        }

        chooseFromGalleryButton.setOnClickListener {
            openGallery()
        }

        cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // traitement du résultat de l'ajout d'un ingrédient
        if (requestCode == ADD_INGREDIENT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("ingredient_name") ?: return
            val quantity = data.getDoubleExtra("ingredient_quantity", 0.0)
            val unit = data.getStringExtra("ingredient_unit") ?: return

            val newIngredient = Ingredient(nom = name, quantite = quantity, unite = unit)
            ingredientsList.add(newIngredient)
            ingredientAdapter.notifyDataSetChanged()

            // traitement du résultat de l'ajout d'une étape
        } else if (requestCode == ADD_STEP_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val description = data.getStringExtra("step_description") ?: return
            val newStep = Etape(description = description)

            stepsList.add(newStep)
            stepAdapter.notifyDataSetChanged()

            // traitement du résultat de la prise de photo
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // creation d'un nouveau fichier pour stocker l'image
            val photoFile = createImageFile()
            // ouverture du flux d'entrée depuis une URI temporaire
            val inputStream = contentResolver.openInputStream(imageUri)
            // Ouverture du flux de sortie vers le fichier permanent
            val outputStream = photoFile.outputStream()

            // copie du flux de donnée d'entrée vers la sortie
            inputStream?.copyTo(outputStream)
            // affichage de l'image dans l'aperçu
            imagePreview.setImageURI(Uri.fromFile(photoFile))
            // enregistrement du chemin absolu dans l'objet recette
            newRecipe.img = photoFile.absolutePath

            // traitement du résultat de la selection d'image via la galerie
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            val selectedImageUri = data?.data

            if (selectedImageUri != null) {
                val inputStream = contentResolver.openInputStream(selectedImageUri)
                val photoFile = createImageFile()
                val outputStream = photoFile.outputStream()

                // gestion automatique de la fermeture des flux
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                imagePreview.setImageURI(Uri.fromFile(photoFile))
                newRecipe.img = photoFile.absolutePath
            }
        }
    }

    // sauvegarde de la recette dans la database
    private fun saveRecipeToDatabase(title: String, nbPieces: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            newRecipe.title = title
            newRecipe.pieces = nbPieces
            val recetteId = recetteDao.insertRecette(newRecipe)


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

    // ouverture de la camera et prise de photo
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile()
        imageUri = FileProvider.getUriForFile(this, "$packageName.provider", photoFile)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    // creation du fichier image
    private fun createImageFile(): File {
        val timestamp = System.currentTimeMillis().toString()
        val fileName = "recette_$timestamp.jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // ouverture de la galerie
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
}
