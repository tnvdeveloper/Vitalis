package com.tnvdeveloper.vitalis

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tnvdeveloper.vitalis.screens.AddRecordScreen
import com.tnvdeveloper.vitalis.screens.HomeScreen
import com.tnvdeveloper.vitalis.screens.StatsScreen
import com.tnvdeveloper.vitalis.ui.theme.VitalisTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitalisTheme {
                VitalisApp()
            }
        }
    }
}

@Composable
fun VitalisApp(viewModel: BloodPressureViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val records by viewModel.allRecords.collectAsState()

    Scaffold(
        bottomBar = {
            VitalisBottomNav(
                currentRoute = navController.currentDestination?.route,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    records = records,
                    onAddClick = { navController.navigate("add") }
                )
            }
            composable("stats") {
                StatsScreen(
                    records = records,
                    selectedPeriod = 7,
                    onPeriodChange = { /* TODO */ }
                )
            }
            composable("add") {
                AddRecordScreen(
                    onSave = { record ->
                        viewModel.addRecord(record)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun VitalisBottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Главная") }
        )
        NavigationBarItem(
            selected = currentRoute == "stats",
            onClick = { onNavigate("stats") },
            icon = { Icon(Icons.Default.ShowChart, contentDescription = null) },
            label = { Text("Статистика") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Settings */ },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Настройки") }
        )
    }
}
