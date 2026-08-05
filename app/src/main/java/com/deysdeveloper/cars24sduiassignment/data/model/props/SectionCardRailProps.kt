package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class SectionCardRailProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("cards") val cards: List<SectionCard> = emptyList()
)

data class SectionCard(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("action") val action: Action? = null
)
