package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class FeaturedCarCardProps(
    @SerializedName("tag") val tag: String = "BUY USED CAR",
    @SerializedName("title") val title: String = "",
    @SerializedName("subtitle") val subtitle: String = "",
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("specs") val specs: List<String> = emptyList(),
    @SerializedName("cta_text") val ctaText: String = "View Details",
    @SerializedName("action") val action: Action? = null
)
