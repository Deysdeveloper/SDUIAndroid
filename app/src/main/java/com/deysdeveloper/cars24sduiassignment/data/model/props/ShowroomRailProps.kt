package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class ShowroomRailProps(
    @SerializedName("title") val title: String = "",
    @SerializedName("showrooms") val showrooms: List<Showroom> = emptyList()
)

data class Showroom(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("distance") val distance: String = "",
    @SerializedName("cars_count") val carsCount: String? = null,
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("closes_at") val closesAt: String? = null,
    // Generic actions list — index 0 = call, index 1 = view
    @SerializedName("actions") val actions: List<Action> = emptyList()
)
