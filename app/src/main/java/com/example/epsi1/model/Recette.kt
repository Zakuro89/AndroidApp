package com.example.epsi1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recette(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,


    )