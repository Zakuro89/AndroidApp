package com.example.epsi1.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.epsi1.model.Etape

@Dao
interface EtapeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEtape(etape: Etape)

    @Query("SELECT * FROM step_table WHERE recetteID = :recetteId")
    suspend fun getEtapesForRecette(recetteId: Long): List<Etape>

    @Query("DELETE FROM step_table WHERE recetteId = :recetteId")
    suspend fun deleteEtapesForRecette(recetteId: Long)

}