package com.deysdeveloper.cars24sduiassignment.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.deysdeveloper.cars24sduiassignment.ui.action.handleAction
import com.deysdeveloper.cars24sduiassignment.ui.renderer.SDUIRenderer
import com.deysdeveloper.cars24sduiassignment.ui.state.HomeUiState
import com.deysdeveloper.cars24sduiassignment.ui.viewmodel.HomeViewModel

private val Cars24Red = Color(0xFFE31837)

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activeFilter by viewModel.activeFilter.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Central onAction lambda — all taps in every composable flow through here
    val onAction = { action: com.deysdeveloper.cars24sduiassignment.data.model.Action ->
        handleAction(
            action = action,
            navController = navController,
            context = context,
            onFilterSections = viewModel::filterSections
        )
    }

    when (val state = uiState) {
        is HomeUiState.Loading -> LoadingContent()

        is HomeUiState.Error -> ErrorContent(
            message = state.message,
            onRetry = viewModel::loadScreen
        )

        is HomeUiState.Success -> SDUIRenderer(
            components = state.screen.components,
            activeFilter = activeFilter,
            gson = viewModel.gson,
            onAction = onAction
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Cars24Red)
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                colors = ButtonDefaults.buttonColors(containerColor = Cars24Red)
            ) {
                Text("Retry", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
