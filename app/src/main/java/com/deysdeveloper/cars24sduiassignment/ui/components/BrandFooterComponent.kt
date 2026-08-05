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
import com.deysdeveloper.cars24sduiassignment.data.model.props.BrandFooterProps

// Stub — replaced in Task 17
@Composable
fun BrandFooterComponent(props: BrandFooterProps) {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(props.text, style = MaterialTheme.typography.bodyMedium)
    }
}
