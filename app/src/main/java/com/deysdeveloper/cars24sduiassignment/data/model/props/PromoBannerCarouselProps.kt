package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class PromoBannerCarouselProps(
    @SerializedName("auto_scroll") val autoScroll: Boolean = true,
    @SerializedName("interval_ms") val intervalMs: Long = 3000L,
    @SerializedName("banners") val banners: List<Banner> = emptyList()
)

data class Banner(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("tag") val tag: String? = null,
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("cta_text") val ctaText: String? = null,
    @SerializedName("bg_color") val bgColor: String? = null,
    @SerializedName("action") val action: Action? = null
)
