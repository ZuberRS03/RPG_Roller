package com.example.rpghelper.presentation.screen_main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rpghelper.presentation.screen_main.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val diceType by viewModel.diceType.collectAsState()
    val diceCount by viewModel.diceCount.collectAsState()
    val lastRoll by viewModel.lastRoll.collectAsState()

    val diceOptions = listOf(4, 6, 8, 10, 12, 20, 100)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Wybierz typ kości", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            diceOptions.forEach { type ->
                Button(onClick = { viewModel.setDiceType(type) }) {
                    Text("d$type")
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Liczba kości: $diceCount")

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { viewModel.setDiceCount((diceCount - 1).coerceAtLeast(1)) }) {
                Text("-")
            }
            Button(onClick = { viewModel.setDiceCount(diceCount + 1) }) {
                Text("+")
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { viewModel.rollDice() }) {
            Text("🎲 Rzuć!")
        }

        if (lastRoll.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            Text("Wynik: ${lastRoll.joinToString(", ")}", style = MaterialTheme.typography.titleLarge)
        }
    }
}