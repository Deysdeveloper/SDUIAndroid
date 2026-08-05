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
import com.deysdeveloper.cars24sduiassignment.data.model.props.TabbedCarListingProps

// Stub — replaced in Task 14
@Composable
fun TabbedCarListingComponent(props: TabbedCarListingProps, onAction: (Action) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Tabbed listing: ${props.title}", style = MaterialTheme.typography.bodyMedium)
    }
}
