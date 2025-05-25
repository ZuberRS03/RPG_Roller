package com.example.rpghelper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "roll_results")
data class RollResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val diceType: Int,     // np. 6 dla d6
    val diceCount: Int,
    val result: List<Int>, // np. [3, 5, 1]
    val timestamp: String  // np. ISO-8601 format daty/godziny
)