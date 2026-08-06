package com.deysdeveloper.cars24sduiassignment.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.deysdeveloper.cars24sduiassignment.ui.action.handleAction
import com.deysdeveloper.cars24sduiassignment.ui.renderer.SDUIRenderer
import com.deysdeveloper.cars24sduiassignment.ui.state.HomeUiState
import com.deysdeveloper.cars24sduiassignment.ui.viewmodel.HomeViewModel

private val Cars24Blue = Color(0xFF3535D4)

private data class HomeNavItem(val label: String, val icon: ImageVector)
private val bottomNavItems = listOf(
    HomeNavItem("Home", Icons.Default.Home),
    HomeNavItem("Activity", Icons.Default.List),
    HomeNavItem("My Garage", Icons.Default.Add),
    HomeNavItem("Showrooms", Icons.Default.ShoppingCart),
    HomeNavItem("Explore", Icons.Default.Search)
)

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activeFilter by viewModel.activeFilter.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedNav by rememberSaveable { mutableStateOf("Home") }

    val onAction = { action: com.deysdeveloper.cars24sduiassignment.data.model.Action ->
        handleAction(
            action = action,
            navController = navController,
            context = context,
            onFilterSections = viewModel::filterSections
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                bottomNavItems.forEach { item: HomeNavItem ->
                    NavigationBarItem(
                        selected = selectedNav == item.label,
                        onClick = { selectedNav = item.label },
                        icon = {
                            Icon(item.icon, item.label, modifier = Modifier.size(22.dp))
                        },
                        label = { Text(item.label, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Cars24Blue,
                            selectedTextColor = Cars24Blue,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is HomeUiState.Loading -> LoadingContent(innerPadding)
            is HomeUiState.Error -> ErrorContent(
                message = state.message,
                padding = innerPadding,
                onRetry = viewModel::loadScreen
            )
            is HomeUiState.Success -> {
                LaunchedEffect(Unit) {
                    Log.d("SDUI_PERF", "HomeScreen first frame rendered — ${state.screen.components.size} components visible")
                }
                SDUIRenderer(
                    components = state.screen.components,
                    activeFilter = activeFilter,
                    gson = viewModel.gson,
                    onAction = onAction,
                    contentPadding = innerPadding
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(padding: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Cars24Blue)
    }
}

@Composable
private fun ErrorContent(message: String, padding: PaddingValues, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Oops! Something went wrong.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Cars24Blue)
            ) {
                Text("Retry", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
