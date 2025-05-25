package com.example.rpghelper.presentation.screen_presets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpghelper.data.model.RollPreset
import com.example.rpghelper.domain.repository.DiceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PresetsViewModel(private val repository: DiceRepository) : ViewModel() {

    val presets: StateFlow<List<RollPreset>> = repository
        .getAllPresets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addPreset(name: String, diceType: Int, diceCount: Int) {
        val preset = RollPreset(name = name, diceType = diceType, diceCount = diceCount)
        viewModelScope.launch {
            repository.addPreset(preset)
        }
    }

    fun deletePreset(preset: RollPreset) {
        viewModelScope.launch {
            repository.deletePreset(preset)
        }
    }
}