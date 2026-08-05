package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class TabbedCarListingProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("tabs") val tabs: List<ListingTab> = emptyList(),
    @SerializedName("carsByTab") val carsByTab: Map<String, List<Car>> = emptyMap()
)

data class ListingTab(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = ""
)

data class Car(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("specs") val specs: List<String> = emptyList(),
    @SerializedName("isFavorite") val isFavorite: Boolean = false,
    @SerializedName("action") val action: Action? = null
)
