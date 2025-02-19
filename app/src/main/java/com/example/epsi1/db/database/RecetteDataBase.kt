package com.example.epsi1.db.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.epsi1.db.dao.EtapeDao
import com.example.epsi1.db.dao.IngredientDao
import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.Recette

@Database(entities = [Recette::class, Ingredient::class, Etape::class], version = 1)
abstract class RecetteDatabase : RoomDatabase() {

    abstract fun recetteDao(): RecetteDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun etapeDao(): EtapeDao

    companion object {
        private lateinit var INSTANCE: RecetteDatabase

        @Synchronized
        fun getDatabase(context: Context): RecetteDatabase {
            if(!::INSTANCE.isInitialized){
                INSTANCE = Room.databaseBuilder(context, RecetteDatabase::class.java, "recette.db").build()
            }
            return INSTANCE
        }
    }

}