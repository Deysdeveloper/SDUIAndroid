package com.deysdeveloper.cars24sduiassignment.data.model.props

import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.google.gson.annotations.SerializedName

data class LoanTenureSelectorProps(
    @SerializedName("title") val title: String = "Car Loans",
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("car_price") val carPrice: String = "₹0",
    @SerializedName("default_tenure_months") val defaultTenureMonths: Int = 48,
    @SerializedName("down_payment_pct") val downPaymentPct: Int = 20,
    @SerializedName("interest_rate_pct") val interestRatePct: Double = 9.5,
    @SerializedName("tenures") val tenures: List<TenureOption> = emptyList(),
    @SerializedName("cta_text") val ctaText: String = "Apply for Loan",
    @SerializedName("action") val action: Action? = null
)

data class TenureOption(
    @SerializedName("months") val months: Int = 12,
    @SerializedName("label") val label: String = "",     // e.g. "1 Year"
    @SerializedName("badge") val badge: String? = null   // e.g. "Best Value"
)
