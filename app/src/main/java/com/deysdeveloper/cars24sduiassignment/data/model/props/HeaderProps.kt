package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class HeaderProps(
    @SerializedName("location") val location: String = "",
    @SerializedName("searchHint") val searchHint: String = "Search for cars",
    @SerializedName("avatarUrl") val avatarUrl: String? = null,
    @SerializedName("locationAction") val locationAction: Action? = null,
    @SerializedName("searchAction") val searchAction: Action? = null,
    @SerializedName("avatarAction") val avatarAction: Action? = null
)
