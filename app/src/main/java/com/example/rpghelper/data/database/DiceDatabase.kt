package com.example.rpghelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rpghelper.data.database.dao.*
import com.example.rpghelper.data.model.*
import com.example.rpghelper.util.Converters

@Database(
    entities = [RollResult::class, RollPreset::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DiceDatabase : RoomDatabase() {
    abstract fun rollHistoryDao(): RollHistoryDao
    abstract fun presetDao(): PresetDao
}