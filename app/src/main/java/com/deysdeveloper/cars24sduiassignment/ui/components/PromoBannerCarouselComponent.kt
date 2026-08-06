package com.deysdeveloper.cars24sduiassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.props.Banner
import com.deysdeveloper.cars24sduiassignment.data.model.props.PromoBannerCarouselProps
import kotlinx.coroutines.delay

private val DotInactive = Color(0xFFCCCCCC)
private val DotActive = Color(0xFFE31837)
private val DefaultBannerBg = Color(0xFF1B2B8A)

@Composable
fun PromoBannerCarouselComponent(props: PromoBannerCarouselProps, onAction: (Action) -> Unit) {
    if (props.banners.isEmpty()) return

    val pageCount = props.banners.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    if (props.autoScroll) {
        LaunchedEffect(pagerState.currentPage) {
            delay(props.intervalMs)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pageCount)
        }
    }

    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
            BannerPage(banner = props.banners[page], onAction = onAction)
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) DotActive else DotInactive)
                )
            }
        }
    }
}

@Composable
private fun BannerPage(banner: Banner, onAction: (Action) -> Unit) {
    val bgColor = parseHexColor(banner.bgColor) ?: DefaultBannerBg

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(bgColor)
            .clickable { banner.action?.let(onAction) }
    ) {
        // Background image (if provided)
        if (banner.imageUrl.isNotBlank()) {
            AsyncImage(
                model = banner.imageUrl,
                contentDescription = banner.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Gradient so text is always readable
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(bgColor.copy(alpha = 0.85f), Color.Transparent)
                        )
                    )
            )
        }

        // Text overlay
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(20.dp)
        ) {
            if (banner.tag != null) {
                Text(
                    text = banner.tag,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.75f),
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.height(4.dp))
            }
            if (banner.title != null) {
                Text(
                    text = banner.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 20.sp
                )
            }
            if (banner.subtitle != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = banner.subtitle,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f)
                )
            }
            if (banner.ctaText != null) {
                Spacer(Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = banner.ctaText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = bgColor
                    )
                }
            }
        }
    }
}
