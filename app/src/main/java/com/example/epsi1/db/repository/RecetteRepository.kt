package com.example.epsi1.db.repository

import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.RecetteEntity
import com.example.epsi1.model.Recette

class RecetteRepository(private val dao: RecetteDao) {

    suspend fun addRecette(recetteEntity: RecetteEntity, ingredients: List<Ingredient>, etapes: List<Etape>) {
        val recetteId = dao.insertRecette(recetteEntity).toInt()
        dao.insertIngredients((ingredients.map { it.copy(recetteId = recetteId) }))
        dao.insertEtapes((etapes.map { it.copy(recetteId = recetteId) }))
    }

    suspend fun getRecetteComplete(id: Long): Recette? = dao.getRecetteComplete(id)

    suspend fun getAllRecette(): List<RecetteEntity> = dao.getAllRecettes()

}