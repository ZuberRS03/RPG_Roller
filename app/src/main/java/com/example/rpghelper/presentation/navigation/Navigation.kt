package com.example.rpghelper.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rpghelper.presentation.screen_main.MainScreen
import com.example.rpghelper.presentation.screen_main.MainViewModel
import com.example.rpghelper.presentation.screen_history.HistoryScreen
import com.example.rpghelper.presentation.screen_history.HistoryViewModel
import com.example.rpghelper.presentation.screen_presets.PresetsScreen
import com.example.rpghelper.presentation.screen_presets.PresetsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    presetsViewModel: PresetsViewModel,
    historyViewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN,
        modifier = modifier
    ) {
        composable(NavRoutes.MAIN) {
            MainScreen(viewModel = mainViewModel)
        }
        composable(NavRoutes.PRESETS) {
            PresetsScreen(
                viewModel = presetsViewModel,
                onPresetSelected = { preset ->
                    mainViewModel.setDiceType(preset.diceType)
                    mainViewModel.setDiceCount(preset.diceCount)
                    mainViewModel.rollDice()
                }
            )
        }
        composable(NavRoutes.HISTORY) {
            HistoryScreen(viewModel = historyViewModel)
        }
    }
}