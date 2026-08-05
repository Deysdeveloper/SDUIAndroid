package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.CategoryTabBarProps

// Stub — replaced in Task 10
@Composable
fun CategoryTabBarComponent(props: CategoryTabBarProps, onAction: (Action) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Tab bar — ${props.tabs.size} tabs", style = MaterialTheme.typography.bodyMedium)
    }
}
