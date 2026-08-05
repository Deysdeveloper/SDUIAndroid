package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.google.gson.annotations.SerializedName

data class BrandFooterProps(
    @SerializedName("text") val text: String = "",
    @SerializedName("backgroundColor") val backgroundColor: String? = null,
    @SerializedName("textColor") val textColor: String? = null
)
