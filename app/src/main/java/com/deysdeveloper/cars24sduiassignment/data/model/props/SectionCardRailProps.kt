package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class SectionCardRailProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("badge") val badge: String? = null,
    @SerializedName("badge_color") val badgeColor: String? = null,
    @SerializedName("cards") val cards: List<SectionCard> = emptyList()
)

data class SectionCard(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = "",
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("bg_color") val bgColor: String? = null,
    @SerializedName("action") val action: Action? = null
)
