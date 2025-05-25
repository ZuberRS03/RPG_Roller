package com.example.rpghelper.presentation.screen_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpghelper.data.model.RollResult
import com.example.rpghelper.domain.repository.DiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class MainViewModel(
    private val repository: DiceRepository
) : ViewModel() {

    private val _diceType = MutableStateFlow(6)  // np. d6
    val diceType: StateFlow<Int> = _diceType

    private val _diceCount = MutableStateFlow(1)
    val diceCount: StateFlow<Int> = _diceCount

    private val _lastRoll = MutableStateFlow<List<Int>>(emptyList())
    val lastRoll: StateFlow<List<Int>> = _lastRoll

    fun setDiceType(type: Int) {
        _diceType.value = type
    }

    fun setDiceCount(count: Int) {
        _diceCount.value = count
    }

    fun rollDice() {
        val type = _diceType.value
        val count = _diceCount.value
        val result = List(count) { Random.nextInt(1, type + 1) }

        _lastRoll.value = result

        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val rollResult = RollResult(
            diceType = type,
            diceCount = count,
            result = result,
            timestamp = timestamp
        )

        viewModelScope.launch {
            repository.addRollResult(rollResult)
        }
    }
}