package com.deysdeveloper.cars24sduiassignment.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ScreenData(
    @SerializedName("screen_id") val screenId: String = "",
    @SerializedName("screen_version") val screenVersion: String = "1.0.0",
    @SerializedName("components") val components: List<Component> = emptyList()
)

data class Component(
    @SerializedName("id") val id: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("props") val props: JsonObject = JsonObject()
)

object ComponentType {
    const val HEADER = "header"
    const val CATEGORY_TAB_BAR = "category_tab_bar"
    const val FEATURED_CAR_CARD = "featured_car_card"
    const val SECTION_CARD_RAIL = "section_card_rail"
    const val SERVICE_GRID = "service_grid"
    const val TABBED_CAR_LISTING = "tabbed_car_listing"
    const val SHOWROOM_RAIL = "showroom_rail"
    const val PROMO_BANNER_CAROUSEL = "promo_banner_carousel"
    const val BRAND_FOOTER = "brand_footer"
}
