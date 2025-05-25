package com.example.rpghelper.util

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListInt(value: List<Int>): String = value.joinToString(",")

    @TypeConverter
    fun toListInt(value: String): List<Int> =
        if (value.isBlank()) emptyList()
        else value.split(",").map { it.toInt() }
}