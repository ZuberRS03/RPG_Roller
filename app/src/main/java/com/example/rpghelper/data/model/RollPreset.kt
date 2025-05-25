package com.example.rpghelper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roll_presets")
data class RollPreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,       // np. "Atak mieczem"
    val diceType: Int,      // np. 20 dla d20
    val diceCount: Int      // np. 2
)