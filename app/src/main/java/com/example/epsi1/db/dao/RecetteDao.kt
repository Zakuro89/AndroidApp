package com.example.epsi1.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.RecetteEntity
import com.example.epsi1.model.Recette

@Dao
interface RecetteDao {

    @Insert
    suspend fun insertRecette(recetteEntity: RecetteEntity): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<Ingredient>)

    @Insert
    suspend fun insertEtapes(etapes: List<Etape>)

    @Update
    suspend fun updateRecette(recetteEntity: RecetteEntity)

    @Delete
    suspend fun deleteRecette(recetteEntity: RecetteEntity)


    @Transaction
    @Query("SELECT * FROM recipe_table WHERE id = :id")
    suspend fun getRecetteComplete(id: Long): Recette?

    @Query("SELECT * FROM recipe_table")
    suspend fun getAllRecettes(): List<RecetteEntity>

    @Query("DELETE FROM recipe_table WHERE id = :id")
    suspend fun deleteRecetteById(id: Long)


    @Transaction
    @Query("DELETE FROM ingredient_table WHERE recetteId = :recetteId")
    suspend fun deleteIngredientsByRecetteId(recetteId: Long)

    @Transaction
    @Query("DELETE FROM step_table WHERE recetteId = :recetteId")
    suspend fun deleteEtapesByRecetteId(recetteId: Long)

    @Transaction
    suspend fun deleteRecetteWithDetails(recetteId: Long) {
        deleteIngredientsByRecetteId(recetteId)
        deleteEtapesByRecetteId(recetteId)
        deleteRecetteById(recetteId)
    }


}