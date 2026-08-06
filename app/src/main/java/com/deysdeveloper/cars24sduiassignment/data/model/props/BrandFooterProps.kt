package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.google.gson.annotations.SerializedName

data class BrandFooterProps(
    @SerializedName("headline") val headline: String = "",
    @SerializedName("subtext") val subtext: String? = null,
    @SerializedName("bg_color") val bgColor: String? = null
)
