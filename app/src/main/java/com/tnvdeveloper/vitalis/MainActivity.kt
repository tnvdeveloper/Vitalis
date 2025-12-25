package com.tnvdeveloper.vitalis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tnvdeveloper.vitalis.data.model.BloodPressureRecord
import com.tnvdeveloper.vitalis.screens.AddRecordScreen
import com.tnvdeveloper.vitalis.screens.HomeScreen
import com.tnvdeveloper.vitalis.screens.StatsScreen
import com.tnvdeveloper.vitalis.ui.screens.AddRecordScreen
import com.tnvdeveloper.vitalis.ui.screens.HomeScreen
import com.tnvdeveloper.vitalis.ui.screens.StatsScreen
import com.tnvdeveloper.vitalis.ui.theme.VitalisTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitalisTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VitalisApp()
                }
            }
        }
    }
}

@Composable
fun VitalisApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            VitalisBottomNav(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    records = emptyList(), // TODO: подключить ViewModel
                    onAddClick = { navController.navigate("add") }
                )
            }
            composable("stats") {
                StatsScreen(
                    records = emptyList(), // TODO: подключить ViewModel
                    selectedPeriod = 7,
                    onPeriodChange = {}
                )
            }
            composable("add") {
                AddRecordScreen(
                    onSave = { record ->
                        // TODO: сохранить в БД
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
