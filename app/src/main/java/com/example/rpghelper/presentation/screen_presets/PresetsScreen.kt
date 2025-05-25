package com.example.rpghelper.presentation.screen_presets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rpghelper.data.model.RollPreset
import com.example.rpghelper.ui.theme.AccentTurquoise
import com.example.rpghelper.ui.theme.DarkBackground
import com.example.rpghelper.ui.theme.SelectedButton
import com.example.rpghelper.ui.theme.TextWhite
import com.example.rpghelper.ui.theme.UnselectedButton
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PresetsScreen(
    viewModel: PresetsViewModel,
    onPresetSelected: (RollPreset, navigateToMain: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val presets by viewModel.presets.collectAsState()

    var name by remember { mutableStateOf("") }
    var diceType by remember { mutableStateOf(6) }
    var diceCount by remember { mutableStateOf(1) }

    val diceOptions = listOf(4, 6, 8, 10, 12, 20, 100)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Dodaj preset", style = MaterialTheme.typography.titleMedium, color = TextWhite)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa presetu", color = TextWhite) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = AccentTurquoise,
                unfocusedBorderColor = UnselectedButton,
                focusedContainerColor = DarkBackground,
                unfocusedContainerColor = DarkBackground
            )
        )

        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            diceOptions.forEach { type ->
                val isSelected = type == diceType
                Button(
                    onClick = { diceType = type },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) SelectedButton else UnselectedButton,
                        contentColor = if (isSelected) Color.Black else TextWhite
                    )
                ) {
                    Text("d$type")
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { diceCount = (diceCount - 1).coerceAtLeast(1) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTurquoise,
                    contentColor = Color.Black
                )
            ) { Text("-") }

            Text("x$diceCount", color = TextWhite)

            Button(
                onClick = { diceCount++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTurquoise,
                    contentColor = Color.Black
                )
            ) { Text("+") }
        }

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.addPreset(name, diceType, diceCount)
                    name = ""
                    diceType = 6
                    diceCount = 1
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentTurquoise,
                contentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zapisz preset")
        }

        Divider(color = TextWhite.copy(alpha = 0.2f))

        Text("Zapisane presety", style = MaterialTheme.typography.titleMedium, color = TextWhite)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(presets) { preset ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = UnselectedButton)
                ) {
                    Row(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${preset.name}: ${preset.diceCount}×d${preset.diceType}", color = TextWhite)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onPresetSelected(preset, true) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AccentTurquoise,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text("Wczytaj")
                            }
                            Button(
                                onClick = { viewModel.deletePreset(preset) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkBackground,
                                    contentColor = TextWhite
                                )
                            ) {
                                Text("Usuń")
                            }
                        }
                    }
                }
            }
        }
    }
}