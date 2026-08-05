package com.deysdeveloper.cars24sduiassignment.data.model

import com.google.gson.annotations.SerializedName

data class Action(
    @SerializedName("type") val type: String = "",
    @SerializedName("destination") val destination: String? = null,
    @SerializedName("params") val params: Map<String, String>? = null
)

object ActionType {
    const val NAVIGATE = "navigate"
    const val CALL = "call"
    const val FILTER_SECTIONS = "filter_sections"
}
