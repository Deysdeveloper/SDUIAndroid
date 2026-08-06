package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class HeaderProps(
    @SerializedName("location") val location: String = "",
    @SerializedName("location_selectable") val locationSelectable: Boolean = true,
    @SerializedName("search_hint") val searchHint: String = "Search for cars",
    @SerializedName("avatar_text") val avatarText: String = "",
    @SerializedName("action") val action: Action? = null
)
