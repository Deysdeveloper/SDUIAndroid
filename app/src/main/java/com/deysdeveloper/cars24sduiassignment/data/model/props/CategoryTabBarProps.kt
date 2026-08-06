package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class CategoryTabBarProps(
    @SerializedName("default_selected") val defaultSelected: String = "all",
    @SerializedName("tabs") val tabs: List<CategoryTab> = emptyList(),
    // Shared filter action — target_ids lists all filterable section IDs
    @SerializedName("action") val action: Action? = null
)

data class CategoryTab(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = "",
    @SerializedName("icon_url") val iconUrl: String? = null
)
