package com.example.rpghelper.presentation.screen_history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rpghelper.data.model.RollResult

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val rollHistory by viewModel.rollHistory.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Historia rzutów", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { viewModel.clearHistory() }) {
                Text("Wyczyść")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (rollHistory.isEmpty()) {
            Text("Brak zapisanych rzutów.", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(rollHistory) { roll ->
                    RollHistoryItem(roll)
                }
            }
        }
    }
}

@Composable
fun RollHistoryItem(roll: RollResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Rzut: ${roll.diceCount}×d${roll.diceType}")
            Text("Wynik: ${roll.result.joinToString(", ")}")
            Text("Czas: ${roll.timestamp}", style = MaterialTheme.typography.bodySmall)
        }
    }
}