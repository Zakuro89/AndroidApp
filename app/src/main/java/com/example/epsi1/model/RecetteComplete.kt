package com.example.epsi1.model

import androidx.room.Embedded
import androidx.room.Relation

data class RecetteComplete(
    @Embedded val recette: Recette,

    @Relation(parentColumn = "id", entityColumn = "recetteId")
    val ingredients: List<Ingredient>,

    @Relation(parentColumn = "id", entityColumn = "recetteId")
    val etapes: List<Etape>
)
