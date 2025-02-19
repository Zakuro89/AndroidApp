package com.example.epsi1.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.Recette
import com.example.epsi1.model.RecetteComplete

@Dao
interface RecetteDao {

    @Insert
    suspend fun insertRecette(recette: Recette): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<Ingredient>)

    @Insert
    suspend fun insertEtapes(etapes: List<Etape>)

    @Update
    suspend fun updateRecette(recette: Recette)

    @Delete
    suspend fun deleteRecette(recette:Recette)

    @Transaction
    @Query("SELECT * FROM recipe_table WHERE id = :id")
    suspend fun  getRecetteComplete(id: Int): RecetteComplete?

    @Query("SELECT * FROM recipe_table")
    suspend fun  getAllRecettes(): List<Recette>






}