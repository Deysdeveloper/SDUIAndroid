package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class FeaturedCarCardProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("chips") val chips: List<String> = emptyList(),
    @SerializedName("ctaLabel") val ctaLabel: String = "View Details",
    @SerializedName("action") val action: Action? = null
)
