package com.example.rpghelper.presentation.screen_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rpghelper.data.model.RollResult
import com.example.rpghelper.ui.theme.AccentTurquoise
import com.example.rpghelper.ui.theme.DarkBackground
import com.example.rpghelper.ui.theme.TextWhite
import com.example.rpghelper.ui.theme.UnselectedButton

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val rollHistory by viewModel.rollHistory.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Historia rzutów",
                style = MaterialTheme.typography.titleLarge,
                color = TextWhite
            )
            Button(
                onClick = { viewModel.clearHistory() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTurquoise,
                    contentColor = Color.Black
                )
            ) {
                Text("Wyczyść")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (rollHistory.isEmpty()) {
            Text("Brak zapisanych rzutów.", style = MaterialTheme.typography.bodyLarge, color = TextWhite)
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
        colors = CardDefaults.cardColors(containerColor = UnselectedButton)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Rzut: ${roll.diceCount}×d${roll.diceType}", color = TextWhite)
            Text("Wynik: ${roll.result.joinToString(", ")}", color = TextWhite)
            Text("Czas: ${roll.timestamp}", style = MaterialTheme.typography.bodySmall, color = TextWhite.copy(alpha = 0.7f))
        }
    }
}