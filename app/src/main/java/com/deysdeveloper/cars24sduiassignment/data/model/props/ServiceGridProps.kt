package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class ServiceGridProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("columns") val columns: Int = 3,
    @SerializedName("items") val items: List<ServiceItem> = emptyList()
)

data class ServiceItem(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = "",
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("action") val action: Action? = null
)
