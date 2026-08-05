package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.CategoryTab
import com.deysdeveloper.cars24sduiassignment.data.model.props.CategoryTabBarProps

private val TabsBg = Cars24Blue           // same blue as header — seamless continuation
private val CircleBg = Color(0x33FFFFFF)  // white 20% tint for unselected icon bg

@Composable
fun CategoryTabBarComponent(props: CategoryTabBarProps, onAction: (Action) -> Unit) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.background(TabsBg)
    ) {
        itemsIndexed(props.tabs) { index, tab ->
            TabItem(
                tab = tab,
                isSelected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    tab.action?.let(onAction)
                }
            )
        }
    }
}

@Composable
private fun TabItem(tab: CategoryTab, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular icon container
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(CircleBg),
            contentAlignment = Alignment.Center
        ) {
            if (tab.iconUrl != null) {
                AsyncImage(
                    model = tab.iconUrl,
                    contentDescription = tab.label,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = tab.label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Underline indicator for selected tab
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(2.dp)
                .background(
                    if (isSelected) Color.White else Color.Transparent
                )
        )
    }
}
