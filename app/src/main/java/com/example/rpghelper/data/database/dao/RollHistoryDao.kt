package com.example.rpghelper.data.database.dao

import androidx.room.*
import com.example.rpghelper.data.model.RollResult
import kotlinx.coroutines.flow.Flow

@Dao
interface RollHistoryDao {
    @Insert
    suspend fun insert(result: RollResult)

    @Query("SELECT * FROM roll_results ORDER BY timestamp DESC")
    fun getAll(): Flow<List<RollResult>>

    @Query("DELETE FROM roll_results")
    suspend fun clear()
}