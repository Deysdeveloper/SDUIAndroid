package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.LoanTenureSelectorProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.TenureOption
import kotlin.math.pow

private val LoanBlue = Color(0xFF3535D4)
private val LightBlue = Color(0xFFEEEEFB)
private val SurfaceGrey = Color(0xFFF5F5F5)

/**
 * Loan Tenure Selector — SDUI component.
 *
 * Renders a card showing monthly EMI for the current tenure.
 * Tapping "Change Tenure" opens a [ModalBottomSheet] with:
 *   - All tenure options from props as selectable chips
 *   - Live EMI recalculation on selection
 *   - Confirm CTA that fires props.action
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanTenureSelectorComponent(
    props: LoanTenureSelectorProps,
    onAction: (Action) -> Unit
) {
    var selectedMonths by rememberSaveable {
        mutableIntStateOf(props.defaultTenureMonths)
    }
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Derived EMI from selected tenure
    val emi = calculateEmi(
        carPrice = props.carPrice,
        downPaymentPct = props.downPaymentPct,
        annualRatePct = props.interestRatePct,
        tenureMonths = selectedMonths
    )

    val selectedOption = props.tenures.find { it.months == selectedMonths }
    val tenureLabel = selectedOption?.label ?: "${selectedMonths}M"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Section title
        Text(
            text = props.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        props.subtitle?.let {
            Text(it, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        }

        // EMI summary card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceGrey)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                // Car price + down payment row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LabelValue("Car Price", props.carPrice)
                    LabelValue(
                        "Down Payment",
                        "${props.downPaymentPct}%",
                        valueColor = LoanBlue
                    )
                    LabelValue(
                        "Interest Rate",
                        "${props.interestRatePct}% p.a.",
                        valueColor = LoanBlue
                    )
                }

                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFE0E0E0))
                Spacer(Modifier.height(16.dp))

                // EMI + tenure row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Monthly EMI", fontSize = 12.sp, color = Color.Gray)
                        Text(
                            text = emi,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Tenure", fontSize = 12.sp, color = Color.Gray)
                        // Tappable tenure chip — opens bottom sheet
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(LightBlue)
                                .border(1.dp, LoanBlue, RoundedCornerShape(20.dp))
                                .clickable { showSheet = true }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "$tenureLabel ▾",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = LoanBlue
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Apply CTA
                Button(
                    onClick = { props.action?.let(onAction) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LoanBlue)
                ) {
                    Text(
                        text = props.ctaText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }
    }

    // ── Bottom Sheet ─────────────────────────────────────────────────────────
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            TenureBottomSheetContent(
                props = props,
                selectedMonths = selectedMonths,
                currentEmi = emi,
                onTenureSelected = { months ->
                    selectedMonths = months
                },
                onConfirm = {
                    showSheet = false
                    props.action?.let(onAction)
                }
            )
        }
    }
}

@Composable
private fun TenureBottomSheetContent(
    props: LoanTenureSelectorProps,
    selectedMonths: Int,
    currentEmi: String,
    onTenureSelected: (Int) -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp)
    ) {
        // Handle indicator
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFE0E0E0))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Select Loan Tenure",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Choose the number of months for your loan repayment",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        // Tenure option chips
        if (props.tenures.isNotEmpty()) {
            // Grid-style: 2 items per row
            val rows = props.tenures.chunked(2)
            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { option ->
                        TenureChip(
                            option = option,
                            isSelected = option.months == selectedMonths,
                            emi = calculateEmi(
                                carPrice = props.carPrice,
                                downPaymentPct = props.downPaymentPct,
                                annualRatePct = props.interestRatePct,
                                tenureMonths = option.months
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = { onTenureSelected(option.months) }
                        )
                    }
                    // Fill empty slot if odd number of options
                    if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        } else {
            // Fallback: horizontal scroll of month numbers
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(listOf(12, 24, 36, 48, 60, 72)) { months ->
                    SimpleMonthChip(
                        months = months,
                        isSelected = months == selectedMonths,
                        onClick = { onTenureSelected(months) }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider(color = Color(0xFFF0F0F0))
        Spacer(Modifier.height(16.dp))

        // Live EMI preview
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(LightBlue)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Monthly EMI", fontSize = 14.sp, color = LoanBlue)
            Text(
                text = currentEmi,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LoanBlue
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onConfirm,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LoanBlue)
        ) {
            Text(
                text = "Confirm & Apply",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun TenureChip(
    option: TenureOption,
    isSelected: Boolean,
    emi: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bg = if (isSelected) LightBlue else Color.White
    val borderColor = if (isSelected) LoanBlue else Color(0xFFE0E0E0)
    val textColor = if (isSelected) LoanBlue else Color.Black

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            // Badge (e.g. "Best Value")
            option.badge?.let {
                Text(
                    text = it,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(LoanBlue)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Spacer(Modifier.height(6.dp))
            }
            Text(
                text = option.label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = emi,
                fontSize = 13.sp,
                color = if (isSelected) LoanBlue else Color.Gray,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
private fun SimpleMonthChip(months: Int, isSelected: Boolean, onClick: () -> Unit) {
    val bg = if (isSelected) LoanBlue else Color(0xFFF0F0F0)
    val textColor = if (isSelected) Color.White else Color.Black
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text("${months}M", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = textColor)
    }
}

@Composable
private fun LabelValue(
    label: String,
    value: String,
    valueColor: Color = Color.Black
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 11.sp, color = Color.Gray)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
    }
}

/**
 * Standard flat-rate EMI formula:
 * EMI = P × r × (1+r)^n / ((1+r)^n - 1)
 * where P = principal (after down payment), r = monthly rate, n = tenure in months
 */
private fun calculateEmi(
    carPrice: String,
    downPaymentPct: Int,
    annualRatePct: Double,
    tenureMonths: Int
): String {
    val priceNum = carPrice
        .replace("[^0-9.]".toRegex(), "")
        .toDoubleOrNull() ?: return "₹--"
    if (priceNum <= 0 || tenureMonths <= 0) return "₹--"

    val principal = priceNum * (1.0 - downPaymentPct / 100.0)
    val monthlyRate = annualRatePct / 100.0 / 12.0
    val emi = if (monthlyRate == 0.0) {
        principal / tenureMonths
    } else {
        principal * monthlyRate * (1 + monthlyRate).pow(tenureMonths) /
                ((1 + monthlyRate).pow(tenureMonths) - 1)
    }
    return "₹${"%,.0f".format(emi)}/mo"
}
