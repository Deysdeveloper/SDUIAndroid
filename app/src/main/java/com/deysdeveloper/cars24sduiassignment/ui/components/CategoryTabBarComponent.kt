package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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

private val Cars24Red = Color(0xFFE31837)
private val TabSelectedBg = Color(0xFFFFF0F2)

@Composable
fun CategoryTabBarComponent(props: CategoryTabBarProps, onAction: (Action) -> Unit) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.background(Color.White)
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
private fun TabItem(
    tab: CategoryTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) TabSelectedBg else Color.White
    val borderColor = if (isSelected) Cars24Red else Color(0xFFE0E0E0)
    val textColor = if (isSelected) Cars24Red else Color(0xFF555555)
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (tab.iconUrl != null) {
            AsyncImage(
                model = tab.iconUrl,
                contentDescription = tab.label,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Text(
            text = tab.label,
            fontSize = 12.sp,
            fontWeight = fontWeight,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(64.dp)
        )
    }
}
