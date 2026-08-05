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
import com.deysdeveloper.cars24sduiassignment.data.model.props.PromoBannerCarouselProps

// Stub — replaced in Task 16
@Composable
fun PromoBannerCarouselComponent(props: PromoBannerCarouselProps, onAction: (Action) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Banners: ${props.banners.size}", style = MaterialTheme.typography.bodyMedium)
    }
}
