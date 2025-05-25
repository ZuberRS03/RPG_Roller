package com.example.rpghelper.domain.repository

import com.example.rpghelper.data.database.dao.PresetDao
import com.example.rpghelper.data.database.dao.RollHistoryDao
import com.example.rpghelper.data.model.RollPreset
import com.example.rpghelper.data.model.RollResult
import kotlinx.coroutines.flow.Flow

class DiceRepository(
    private val rollHistoryDao: RollHistoryDao,
    private val presetDao: PresetDao
) {

    // Historia
    suspend fun addRollResult(result: RollResult) {
        rollHistoryDao.insert(result)
    }

    fun getRollHistory(): Flow<List<RollResult>> {
        return rollHistoryDao.getAll()
    }

    suspend fun clearHistory() {
        rollHistoryDao.clear()
    }

    // Presety
    suspend fun addPreset(preset: RollPreset) {
        presetDao.insert(preset)
    }

    suspend fun deletePreset(preset: RollPreset) {
        presetDao.delete(preset)
    }

    fun getAllPresets(): Flow<List<RollPreset>> {
        return presetDao.getAll()
    }
}