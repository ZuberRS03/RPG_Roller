package com.example.rpghelper.presentation.screen_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

import com.example.rpghelper.ui.theme.*

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val diceType by viewModel.diceType.collectAsState()
    val diceCount by viewModel.diceCount.collectAsState()
    val isRolling by viewModel.isRolling.collectAsState()
    val animatedDice by viewModel.animatedDice.collectAsState()

    val diceOptions = listOf(4, 6, 8, 10, 12, 20, 100)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Wybierz typ kości", style = MaterialTheme.typography.titleMedium, color = TextWhite)

        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            diceOptions.forEach { type ->
                val isSelected = type == diceType
                Button(
                    onClick = { viewModel.setDiceType(type) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) SelectedButton else UnselectedButton,
                        contentColor = if (isSelected) Color.Black else TextWhite
                    )
                ) {
                    Text("d$type")
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Liczba kości: $diceCount", color = TextWhite)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { viewModel.setDiceCount(diceCount - 1) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTurquoise,
                    contentColor = Color.Black
                )
            ) {
                Text("-")
            }
            Button(
                onClick = { viewModel.setDiceCount(diceCount + 1) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTurquoise,
                    contentColor = Color.Black
                )
            ) {
                Text("+")
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.animateRollDice() },
            enabled = !isRolling,
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentTurquoise,
                contentColor = Color.Black
            )
        ) {
            if (isRolling){
                Text("...", color = TextWhite)
            } else {
                Text("Rzuć!")
            }
        }

        if (animatedDice.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            Text("Kości:", style = MaterialTheme.typography.titleLarge, color = TextWhite)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                animatedDice.forEach { die ->
                    Text(
                        text = die.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextWhite
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            if (!isRolling) {
                Text("Suma: ${animatedDice.sum()}", style = MaterialTheme.typography.titleMedium, color = TextWhite)
            }
        }
    }
}