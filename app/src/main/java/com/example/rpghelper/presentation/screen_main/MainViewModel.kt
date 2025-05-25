package com.example.rpghelper.presentation.screen_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpghelper.data.model.RollResult
import com.example.rpghelper.domain.repository.DiceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class MainViewModel(
    private val repository: DiceRepository
) : ViewModel() {

    private val _diceType = MutableStateFlow(6)
    val diceType: StateFlow<Int> = _diceType

    private val _diceCount = MutableStateFlow(1)
    val diceCount: StateFlow<Int> = _diceCount

    private val _lastRoll = MutableStateFlow<List<Int>>(emptyList())
    val lastRoll: StateFlow<List<Int>> = _lastRoll

    private val _isRolling = MutableStateFlow(false)
    val isRolling: StateFlow<Boolean> = _isRolling

    private val _animatedDice = MutableStateFlow<List<Int>>(emptyList())
    val animatedDice: StateFlow<List<Int>> = _animatedDice

    fun setDiceType(type: Int) {
        _diceType.value = type
    }

    fun setDiceCount(count: Int) {
        _diceCount.value = count.coerceAtLeast(1)
    }

    fun animateRollDice() {
        val type = _diceType.value
        val count = _diceCount.value

        viewModelScope.launch {
            _isRolling.value = true

            // Animowane losowe wartości
            repeat(15) { i ->
                val temp = List(count) { Random.nextInt(1, type + 1) }
                _animatedDice.value = temp
                delay(50L + (i * 10))
            }

            // Rzut końcowy
            val result = List(count) { Random.nextInt(1, type + 1) }
            _lastRoll.value = result
            _animatedDice.value = result
            _isRolling.value = false

            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val rollResult = RollResult(
                diceType = type,
                diceCount = count,
                result = result,
                timestamp = timestamp
            )

            repository.addRollResult(rollResult)
        }
    }
}