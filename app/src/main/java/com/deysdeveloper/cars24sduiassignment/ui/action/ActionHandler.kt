package com.deysdeveloper.cars24sduiassignment.ui.action

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.ActionType
import com.deysdeveloper.cars24sduiassignment.navigation.navigateSafe

/**
 * Central dispatcher for all tap actions in the SDUI system.
 *
 * Every tappable element passes its [Action] here — no composable
 * calls NavController or starts Intents directly.
 *
 * @param action            The action object parsed from JSON props.
 * @param navController     Used for [ActionType.NAVIGATE] actions.
 * @param context           Used for [ActionType.CALL] (dial intent).
 * @param onFilterSections  Called with the raw comma-separated section IDs
 *                          string for [ActionType.FILTER_SECTIONS]; the
 *                          ViewModel converts it to a Set<String>.
 */
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
            val phone = action.params?.get("phone") ?: return
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            context.startActivity(dialIntent)
        }

        ActionType.FILTER_SECTIONS -> {
            val sections = action.params?.get("sections") ?: ""
            onFilterSections(sections)
        }

        else -> {
            // Unknown action type — silently no-op, never crash.
        }
    }
}
