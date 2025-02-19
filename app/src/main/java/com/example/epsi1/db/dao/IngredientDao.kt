package com.example.epsi1.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.epsi1.model.Ingredient

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient_table WHERE recetteId = :recetteId")
    suspend fun getIngredientsForRecette(recetteId: Long): List<Ingredient>

    @Query("DELETE FROM ingredient_table WHERE recetteId = :recetteId")
    suspend fun deleteIngredientsForRecette(recetteId: Long)

}