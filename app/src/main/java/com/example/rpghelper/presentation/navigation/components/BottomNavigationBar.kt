package com.example.rpghelper.presentation.navigation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import com.example.rpghelper.presentation.navigation.NavRoutes

data class NavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomNavigationBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    val items = listOf(
        NavItem(NavRoutes.MAIN, Icons.Filled.SportsEsports, "Rzuty"),
        NavItem(NavRoutes.PRESETS, Icons.Filled.Star, "Presety"),
        NavItem(NavRoutes.HISTORY, Icons.Filled.List, "Historia")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}