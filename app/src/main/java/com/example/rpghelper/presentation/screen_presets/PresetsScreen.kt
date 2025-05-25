package com.example.rpghelper.presentation.screen_presets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rpghelper.data.model.RollPreset

@Composable
fun PresetsScreen(
    viewModel: PresetsViewModel,
    onPresetSelected: (RollPreset) -> Unit,
    modifier: Modifier = Modifier
) {
    val presets by viewModel.presets.collectAsState()

    var name by remember { mutableStateOf("") }
    var diceType by remember { mutableStateOf(6) }
    var diceCount by remember { mutableStateOf(1) }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Dodaj preset", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa presetu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { diceType = 4 }) { Text("d4") }
            Button(onClick = { diceType = 6 }) { Text("d6") }
            Button(onClick = { diceType = 8 }) { Text("d8") }
            Button(onClick = { diceType = 10 }) { Text("d10") }
            Button(onClick = { diceType = 12 }) { Text("d12") }
            Button(onClick = { diceType = 20 }) { Text("d20") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { diceCount = (diceCount - 1).coerceAtLeast(1) }) { Text("-") }
            Text("x$diceCount")
            Button(onClick = { diceCount++ }) { Text("+") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.addPreset(name, diceType, diceCount)
                    name = ""
                    diceType = 6
                    diceCount = 1
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zapisz preset")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        Text("Zapisane presety", style = MaterialTheme.typography.titleMedium)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(presets) { preset ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${preset.name}: ${preset.diceCount}×d${preset.diceType}")
                        }
                        Row {
                            Button(onClick = { onPresetSelected(preset) }) {
                                Text("Rzuć")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { viewModel.deletePreset(preset) }) {
                                Text("Usuń")
                            }
                        }
                    }
                }
            }
        }
    }
}