package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.FeaturedCarCardProps

private val ChipBg = Color(0xFFF0F0F0)
private val ChipBorder = Color(0xFFDDDDDD)
private val PriceBadge = Color(0xFF2E7D32)
private val CategoryLabel = Color(0xFF3535D4)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturedCarCardComponent(props: FeaturedCarCardProps, onAction: (Action) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { props.action?.let(onAction) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // "BUY USED CAR" category label
        Text(
            text = "BUY USED CAR",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = CategoryLabel,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 14.dp, top = 12.dp, bottom = 6.dp)
        )

        // Split row: image left, content right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .padding(bottom = 14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Left: car image + price badge
            Box(modifier = Modifier.width(130.dp)) {
                AsyncImage(
                    model = props.imageUrl,
                    contentDescription = props.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(130.dp)
                        .height(90.dp)
                )
                // Price badge at bottom of image
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .clip(RoundedCornerShape(4.dp))
                        .background(PriceBadge)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "₹ ${props.price}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Right: title + subtitle + chips + CTA
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = props.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Inspected · Certified · Best Price",
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Spec chips
                FlowRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    props.chips.forEach { chip ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .border(1.dp, ChipBorder, RoundedCornerShape(4.dp))
                                .background(ChipBg)
                                .padding(horizontal = 7.dp, vertical = 3.dp)
                        ) {
                            Text(text = chip, fontSize = 10.sp, color = Color(0xFF444444))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // CTA link
                Text(
                    text = props.ctaLabel,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CategoryLabel
                )
            }
        }
    }
}
