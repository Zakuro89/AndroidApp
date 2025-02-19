package com.example.epsi1.model

data class Recette (
    val title: String,
    val ingredients: List<Ingredient> = listOf(),
    val steps: List<Etape> = listOf()
)