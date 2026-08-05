package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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

private val PriceBadgeBg = Color(0xFFF0FFF4)
private val PriceBadgeBorder = Color(0xFFC6F6D5)
private val PriceText = Color(0xFF2F855A)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturedCarCardComponent(props: FeaturedCarCardProps, onAction: (Action) -> Unit) {
    // Blue background wrapper — creates visual continuity with header + tab bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cars24Blue)
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { props.action?.let(onAction) },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {

                // Split row: image left, text right
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AsyncImage(
                        model = props.imageUrl,
                        contentDescription = props.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(112.dp, 80.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "BUY USED CAR",
                            color = Cars24Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = props.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            lineHeight = 17.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Inspected · Certified · Best Price",
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        // Spec chips
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            props.chips.forEach { chip ->
                                Surface(
                                    color = Color(0xFFF0F0F0),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = chip,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFEEEEEE))

                // Price + CTA row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Price badge
                    Surface(
                        color = PriceBadgeBg,
                        border = BorderStroke(1.dp, PriceBadgeBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "₹ ${props.price}",
                            color = PriceText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // CTA link + circle arrow
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { props.action?.let(onAction) }
                    ) {
                        Text(
                            text = props.ctaLabel,
                            color = Cars24Blue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Spacer(Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Cars24Blue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
