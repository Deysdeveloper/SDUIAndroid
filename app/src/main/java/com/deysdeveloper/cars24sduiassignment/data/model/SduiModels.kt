package com.deysdeveloper.cars24sduiassignment.data.model

import com.google.gson.annotations.SerializedName

data class SduiPage(
    @SerializedName("title") val title: String = "",
    @SerializedName("components") val components: List<SduiComponent> = emptyList()
)

data class SduiComponent(
    @SerializedName("type") val type: String = "",
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("price") val price: String? = null,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("actionUrl") val actionUrl: String? = null,
    @SerializedName("items") val items: List<SduiComponent>? = null
)

object ComponentType {
    const val BANNER = "banner"
    const val SECTION_HEADER = "section_header"
    const val CAR_CARD = "car_card"
    const val HORIZONTAL_LIST = "horizontal_list"
}
