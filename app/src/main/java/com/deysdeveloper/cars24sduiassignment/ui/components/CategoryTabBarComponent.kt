package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.ActionType
import com.deysdeveloper.cars24sduiassignment.data.model.props.CategoryTab
import com.deysdeveloper.cars24sduiassignment.data.model.props.CategoryTabBarProps

@Composable
fun CategoryTabBarComponent(props: CategoryTabBarProps, onAction: (Action) -> Unit) {
    var selectedId by rememberSaveable { mutableStateOf(props.defaultSelected) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cars24Blue)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        props.tabs.forEach { tab ->
            TabItem(
                tab = tab,
                isSelected = tab.id == selectedId,
                onClick = {
                    selectedId = tab.id
                    // Build filter action from the shared action + selected tab
                    val filterAction = buildFilterAction(tab.id, props)
                    onAction(filterAction)
                }
            )
        }
    }
}

/**
 * Maps the selected tab ID to a filter_sections action.
 * - "all" (or default_selected) → empty filter (show all)
 * - any other tab ID → find the matching target_id by substring match
 */
private fun buildFilterAction(tabId: String, props: CategoryTabBarProps): Action {
    val isAll = tabId == props.defaultSelected || tabId == "all"
    val targetIds = props.action?.targetIds ?: emptyList()

    val sections = if (isAll) {
        ""
    } else {
        // Match target_id that contains the tab id as substring: "buy" → "buy_section"
        targetIds.filter { it.contains(tabId) }.joinToString(",")
    }
    return Action(type = ActionType.FILTER_SECTIONS, params = mapOf("sections" to sections))
}

@Composable
private fun TabItem(tab: CategoryTab, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(bottom = 6.dp)
    ) {
        Text(
            text = tab.label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(2.dp)
                .background(if (isSelected) Color.White else Color.Transparent)
        )
    }
}
