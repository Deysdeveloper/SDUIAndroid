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
import com.deysdeveloper.cars24sduiassignment.data.model.props.HeaderProps

// Stub — replaced in Task 9
@Composable
fun HeaderComponent(props: HeaderProps, onAction: (Action) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Header — ${props.location}", style = MaterialTheme.typography.bodyMedium)
    }
}
