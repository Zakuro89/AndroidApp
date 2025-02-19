package com.example.epsi1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nom: String,
    val quantite: Double,
    val unite: String,
    var recetteId: Int = 0
)

