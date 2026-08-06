package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class TabbedCarListingProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("default_tab") val defaultTab: String = "",
    @SerializedName("view_all_action") val viewAllAction: Action? = null,
    @SerializedName("tabs") val tabs: List<ListingTab> = emptyList()
)

data class ListingTab(
    @SerializedName("id") val id: String = "",
    @SerializedName("label") val label: String = "",
    // Cars are nested inside each tab in the new schema
    @SerializedName("cars") val cars: List<Car> = emptyList()
)

data class Car(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("emi") val emi: String? = null,
    @SerializedName("specs") val specs: List<String> = emptyList(),
    @SerializedName("badge") val badge: String? = null,
    @SerializedName("action") val action: Action? = null
)
