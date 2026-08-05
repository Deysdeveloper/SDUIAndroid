package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class CategoryTabBarProps(
    @SerializedName("tabs") val tabs: List<CategoryTab> = emptyList()
)

data class CategoryTab(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = "",
    @SerializedName("iconUrl") val iconUrl: String? = null,
    @SerializedName("action") val action: Action? = null
)
