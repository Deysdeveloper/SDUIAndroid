package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deysdeveloper.cars24sduiassignment.data.model.props.BrandFooterProps

/**
 * Full-width brand tagline block.
 * Background and text colours are driven entirely by [props] —
 * the composable contains zero hardcoded colour values.
 */
@Composable
fun BrandFooterComponent(props: BrandFooterProps) {
    val bgColor = props.backgroundColor.parseHexColor(fallback = Cars24Blue)
    val textColor = props.textColor.parseHexColor(fallback = Color.White)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(horizontal = 24.dp, vertical = 48.dp)
    ) {
        Column {
            Text(
                text = props.text,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textColor,
                lineHeight = 46.sp,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Made with ❤️ in Gurugram",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor.copy(alpha = 0.85f)
            )
        }
    }
}

/**
 * Safely parses a `#RRGGBB` or `#AARRGGBB` hex string to [Color].
 * Returns [fallback] for null or malformed values — never throws.
 */
internal fun String?.parseHexColor(fallback: Color): Color {
    if (this.isNullOrBlank()) return fallback
    return try {
        val hex = this.trimStart('#')
        val argb = when (hex.length) {
            6 -> "FF$hex".toLong(16)
            8 -> hex.toLong(16)
            else -> return fallback
        }
        Color(argb.toInt())
    } catch (e: NumberFormatException) {
        fallback
    }
}
