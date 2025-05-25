package com.example.rpghelper.presentation.navigation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rpghelper.presentation.navigation.NavRoutes
import com.example.rpghelper.ui.theme.AccentTurquoise
import com.example.rpghelper.ui.theme.BottomBarBackground
import com.example.rpghelper.ui.theme.DarkBackground
import com.example.rpghelper.ui.theme.TextWhite

data class NavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomNavigationBar(navController: NavController,
                        onNavigate: (String) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        NavItem(NavRoutes.MAIN, Icons.Filled.SportsEsports, "Rzuty"),
        NavItem(NavRoutes.PRESETS, Icons.Filled.Star, "Presety"),
        NavItem(NavRoutes.HISTORY, Icons.Filled.List, "Historia")
    )

    NavigationBar(
        containerColor = BottomBarBackground,
        //contentColor = AccentTurquoise
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) AccentTurquoise else TextWhite
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (selected) AccentTurquoise else TextWhite
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
//                selected = currentRoute == item.route,
//                onClick = { onNavigate(item.route) },
//                icon = {
//                    Icon(
//                        imageVector = item.icon,
//                        contentDescription = item.label
//                    )
//                },
//                label = {
//                    Text(text = item.label)
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = AccentTurquoise,
//                    unselectedIconColor = Color.Gray,
//                    selectedTextColor = AccentTurquoise,
//                    unselectedTextColor = Color.Gray,
//                    indicatorColor = DarkBackground
//                )
            )
        }
    }
}