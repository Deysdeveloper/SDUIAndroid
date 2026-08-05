package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class ServiceGridProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("services") val services: List<Service> = emptyList()
)

data class Service(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = "",
    @SerializedName("iconUrl") val iconUrl: String = "",
    @SerializedName("action") val action: Action? = null
)
