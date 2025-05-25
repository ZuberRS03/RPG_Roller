package com.example.rpghelper

import android.content.Context
import androidx.room.Room
import com.example.rpghelper.data.database.DiceDatabase
import com.example.rpghelper.domain.repository.DiceRepository

object AppModule {
    private var database: DiceDatabase? = null
    private var repository: DiceRepository? = null

    fun provideRepository(context: Context): DiceRepository {
        if (repository == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                DiceDatabase::class.java,
                "dice_database"
            ).build()

            repository = DiceRepository(
                rollHistoryDao = database!!.rollHistoryDao(),
                presetDao = database!!.presetDao()
            )
        }
        return repository!!
    }
}