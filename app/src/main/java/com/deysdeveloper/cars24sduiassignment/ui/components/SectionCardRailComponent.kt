package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.SectionCard
import com.deysdeveloper.cars24sduiassignment.data.model.props.SectionCardRailProps

private val DefaultCardBg = Color(0xFF1A237E)

@Composable
fun SectionCardRailComponent(props: SectionCardRailProps, onAction: (Action) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = props.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            // Badge (e.g. "Up to ₹80,000 off")
            if (props.badge != null) {
                val badgeBg = parseHexColor(props.badgeColor) ?: Color(0xFFE63946)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(badgeBg)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(props.badge, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(props.cards, key = { it.id }) { card ->
                SectionCardItem(card = card, onAction = onAction)
            }
        }
    }
}

@Composable
private fun SectionCardItem(card: SectionCard, onAction: (Action) -> Unit) {
    // Use per-card bg_color from JSON, fall back to default blue
    val cardBg = parseHexColor(card.bgColor) ?: DefaultCardBg

    Card(
        modifier = Modifier
            .width(140.dp)
            .height(110.dp)
            .clickable { card.action?.let(onAction) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize().background(cardBg))

            AsyncImage(
                model = card.imageUrl,
                contentDescription = card.label,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(110.dp)
                    .height(75.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(cardBg, cardBg.copy(alpha = 0.4f)),
                            startX = 0f, endX = 300f
                        )
                    )
            )

            Text(
                text = card.label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
                    .width(90.dp)
            )
        }
    }
}

internal fun parseHexColor(hex: String?): Color? {
    if (hex == null) return null
    return runCatching {
        val cleaned = hex.trimStart('#')
        when (cleaned.length) {
            6 -> Color(android.graphics.Color.parseColor("#$cleaned"))
            8 -> Color(android.graphics.Color.parseColor("#$cleaned"))
            else -> null
        }
    }.getOrNull()
}
