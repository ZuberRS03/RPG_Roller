package com.example.rpghelper.presentation.screen_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpghelper.data.model.RollResult
import com.example.rpghelper.domain.repository.DiceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: DiceRepository
) : ViewModel() {

    val rollHistory: StateFlow<List<RollResult>> = repository
        .getRollHistory()
        .map { it.sortedByDescending { r -> r.timestamp } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}