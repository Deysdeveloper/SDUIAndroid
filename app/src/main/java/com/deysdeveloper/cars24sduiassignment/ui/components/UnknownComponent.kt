package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable

/**
 * Fallback for any unrecognised component type from the JSON.
 * Renders an invisible empty Box — never throws, never crashes.
 */
@Composable
fun UnknownComponent() {
    Box {}
}
