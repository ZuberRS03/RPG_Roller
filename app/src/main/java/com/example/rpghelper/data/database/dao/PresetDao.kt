package com.example.rpghelper.data.database.dao

import androidx.room.*
import com.example.rpghelper.data.model.RollPreset
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {
    @Insert
    suspend fun insert(preset: RollPreset)

    @Delete
    suspend fun delete(preset: RollPreset)

    @Query("SELECT * FROM roll_presets ORDER BY name")
    fun getAll(): Flow<List<RollPreset>>
}