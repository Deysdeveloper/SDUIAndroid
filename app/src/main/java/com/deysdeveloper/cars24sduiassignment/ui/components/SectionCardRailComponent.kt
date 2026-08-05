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

// Card background colours mirror the real Cars24 sections
private val BuyCardBg = Color(0xFF1A237E)   // deep blue for Buy car
private val SellCardBg = Color(0xFF1B5E20)  // deep green for Sell car
private val LoanCardBg = Color(0xFF0D47A1)  // mid blue for Loans
private val DefaultCardBg = Color(0xFF3535D4)

private val OfferTagBg = Color(0xFFE31837)

/**
 * Reusable horizontal card rail — used for Buy car, Sell car, Loans,
 * and Trending sections. Differentiated only by [props].
 */
@Composable
fun SectionCardRailComponent(props: SectionCardRailProps, onAction: (Action) -> Unit) {
    // Pick card background colour by section title keyword
    val cardBg = when {
        props.title.contains("buy", ignoreCase = true) ||
        props.title.contains("trending", ignoreCase = true) -> BuyCardBg
        props.title.contains("sell", ignoreCase = true) -> SellCardBg
        props.title.contains("loan", ignoreCase = true) -> LoanCardBg
        else -> DefaultCardBg
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp)
    ) {
        // Section title row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = props.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(props.cards, key = { it.id }) { card ->
                SectionCardItem(card = card, cardBg = cardBg, onAction = onAction)
            }
        }
    }
}

@Composable
private fun SectionCardItem(card: SectionCard, cardBg: Color, onAction: (Action) -> Unit) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(110.dp)
            .clickable { card.action?.let(onAction) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Coloured background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(cardBg)
            )

            // Car image — bottom-right, partial bleed
            AsyncImage(
                model = card.imageUrl,
                contentDescription = card.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(100.dp)
                    .height(70.dp)
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp)
            )

            // Gradient overlay so text is always readable
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(cardBg, cardBg.copy(alpha = 0.4f)),
                            startX = 0f,
                            endX = 300f
                        )
                    )
            )

            // Title text — top-left
            Text(
                text = card.title,
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
