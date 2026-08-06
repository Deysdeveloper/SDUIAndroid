package com.deysdeveloper.cars24sduiassignment.ui.action

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.ActionType
import com.deysdeveloper.cars24sduiassignment.navigation.navigateSafe

fun handleAction(
    action: Action,
    navController: NavController,
    context: Context,
    onFilterSections: (String) -> Unit
) {
    when (action.type) {
        ActionType.NAVIGATE -> {
            val destination = action.destination ?: return
            navController.navigateSafe(destination)
        }

        ActionType.CALL -> {
            // New schema: phone as top-level field; old schema: phone in params map
            val phone = action.phone ?: action.params?.get("phone") ?: return
            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
        }

        ActionType.FILTER_SECTIONS -> {
            // New schema: target_ids list; old schema: sections in params map
            val sections = when {
                !action.targetIds.isNullOrEmpty() -> action.targetIds.joinToString(",")
                else -> action.params?.get("sections") ?: ""
            }
            onFilterSections(sections)
        }

        else -> { /* Unknown action type — silently no-op */ }
    }
}
