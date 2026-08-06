package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.Car
import com.deysdeveloper.cars24sduiassignment.data.model.props.TabbedCarListingProps

private val Cars24Red = Color(0xFFE31837)
private val SelectedTabBg = Color(0xFFE31837)
private val UnselectedTabBg = Color(0xFFF0F0F0)
private val SpecChipBg = Color(0xFFF5F5F5)

@Composable
fun TabbedCarListingComponent(props: TabbedCarListingProps, onAction: (Action) -> Unit) {
    val initialTab = props.defaultTab.ifBlank { props.tabs.firstOrNull()?.id ?: "" }
    var selectedTabId by rememberSaveable { mutableStateOf(initialTab) }

    // Cars are now nested inside each tab object
    val activeCars = props.tabs.find { it.id == selectedTabId }?.cars ?: emptyList()

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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = props.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            if (props.viewAllAction != null) {
                Text(
                    text = "View all",
                    fontSize = 13.sp,
                    color = Cars24Blue,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { props.viewAllAction.let(onAction) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tab selector
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(UnselectedTabBg)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            props.tabs.forEach { tab ->
                val isSelected = tab.id == selectedTabId
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) SelectedTabBg else Color.Transparent)
                        .clickable { selectedTabId = tab.id }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.label,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            activeCars.forEach { car ->
                CarListingCard(car = car, onAction = onAction)
            }
            if (activeCars.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No cars available", color = Color.Gray, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun CarListingCard(car: Car, onAction: (Action) -> Unit) {
    var isFavorite by rememberSaveable(car.id) { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { car.action?.let(onAction) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Badge (e.g. "Cars24 Owned stock")
            if (car.badge != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Cars24Blue.copy(alpha = 0.08f))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(car.badge, fontSize = 11.sp, color = Cars24Blue, fontWeight = FontWeight.Medium)
                }
            }

            Row(modifier = Modifier.padding(10.dp)) {
                AsyncImage(
                    model = car.imageUrl,
                    contentDescription = car.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = car.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (car.subtitle != null) {
                                Text(
                                    text = car.subtitle,
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        IconButton(onClick = { isFavorite = !isFavorite }, modifier = Modifier.size(28.dp)) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favourite",
                                tint = if (isFavorite) Cars24Red else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        car.specs.take(3).forEach { spec ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SpecChipBg)
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(text = spec, fontSize = 10.sp, color = Color(0xFF555555))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = car.price, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    if (car.emi != null) {
                        Text(text = car.emi, fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
