package com.example.epsi1.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.epsi1.model.modelinterface.IRecetteEntity

data class Recette(
    @Embedded val recette: RecetteEntity,

    @Relation(parentColumn = "id", entityColumn = "recetteId", entity = Ingredient::class)
    val ingredients: List<Ingredient>,

    @Relation(parentColumn = "id", entityColumn = "recetteId", entity = Etape::class)
    val etapes: List<Etape>
): IRecetteEntity by recette {

}
