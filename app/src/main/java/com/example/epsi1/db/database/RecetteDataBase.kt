package com.example.epsi1.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.epsi1.db.dao.EtapeDao
import com.example.epsi1.db.dao.IngredientDao
import com.example.epsi1.db.dao.RecetteDao
import com.example.epsi1.model.Etape
import com.example.epsi1.model.Ingredient
import com.example.epsi1.model.RecetteEntity

@Database(entities = [RecetteEntity::class, Ingredient::class, Etape::class], version = 3)
abstract class RecetteDatabase : RoomDatabase() {

    abstract fun recetteDao(): RecetteDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun etapeDao(): EtapeDao

    companion object {
        private lateinit var INSTANCE: RecetteDatabase


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE recipe_table ADD COLUMN img TEXT")
            }
        }

        val MIGRATION_2_3 = object: Migration(2,3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE recipe_table ADD COLUMN pieces INTEGER")
            }
        }

        @Synchronized
        fun getDatabase(context: Context): RecetteDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE =
                    Room
                        .databaseBuilder(context, RecetteDatabase::class.java, "recette.db")
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .build()
            }
            return INSTANCE
        }
    }

}