package com.example.epsi1.db.repository

import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.Recette
import com.example.epsi1.model.RecetteComplete

class RecetteRepository(private val dao: RecetteDao) {

    suspend fun addRecette(recette: Recette, ingredients: List<Ingredient>, etapes: List<Etape>) {
        val recetteId = dao.insertRecette(recette).toInt()
        dao.insertIngredients((ingredients.map { it.copy(recetteId = recetteId) }))
        dao.insertEtapes((etapes.map { it.copy(recetteId = recetteId) }))
    }

    suspend fun getRecetteComplete(id: Int): RecetteComplete? = dao.getRecetteComplete(id)

    suspend fun getAllRecette(): List<Recette> = dao.getAllRecettes()

}