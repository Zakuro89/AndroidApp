package com.example.epsi1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.epsi1.model.modelinterface.IRecetteEntity

@Entity(tableName = "recipe_table")
data class RecetteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    override var title: String,
    override var img: String? = null,

    ): IRecetteEntity