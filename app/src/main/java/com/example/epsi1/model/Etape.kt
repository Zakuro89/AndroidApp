package com.example.epsi1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_table")
data class Etape(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    var recetteId: Int = 0

)