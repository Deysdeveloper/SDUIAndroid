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
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("distance") val distance: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("callAction") val callAction: Action? = null,
    @SerializedName("viewAction") val viewAction: Action? = null
)
