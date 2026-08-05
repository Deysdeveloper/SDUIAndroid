package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class PromoBannerCarouselProps(
    @SerializedName("banners") val banners: List<Banner> = emptyList(),
    @SerializedName("autoScrollIntervalMs") val autoScrollIntervalMs: Long = 3000L
)

data class Banner(
    @SerializedName("id") val id: String = "",
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("action") val action: Action? = null
)
