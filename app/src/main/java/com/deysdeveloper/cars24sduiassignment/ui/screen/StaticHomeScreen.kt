package com.deysdeveloper.cars24sduiassignment.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// ── Theme ──────────────────────────────────────────────────────────────────
private val Cars24Primary = Color(0xFF3535D5)
private val Cars24Green = Color(0xFF1E7C52)

// ── Static buy/sell data ────────────────────────────────────────────────────
private data class ActionCardData(val title: String, val bg: Color, val imageUrl: String)

private val buyCards = listOf(
    ActionCardData("All used cars", Cars24Primary, "https://images.unsplash.com/photo-1574023278969-abb7ab49945c?w=400"),
    ActionCardData("Budget used cars", Cars24Primary, "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=400"),
    ActionCardData("Premium used cars", Cars24Primary, "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=400"),
    ActionCardData("New cars", Cars24Primary, "https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=400")
)

private val sellCards = listOf(
    ActionCardData("Sell your car", Cars24Green, "https://images.unsplash.com/photo-1560037962-08931d95007f?w=400"),
    ActionCardData("Check car valuation", Cars24Green, "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400"),
    ActionCardData("Scrap your car", Cars24Green, "https://images.unsplash.com/photo-1574116404703-ef6bde0cf1b4?w=400"),
    ActionCardData("Exchange car", Cars24Green, "https://images.unsplash.com/photo-1464219789935-c2d9d9aba644?w=400")
)

private data class NavItem(val label: String, val icon: ImageVector)

private val navItems = listOf(
    NavItem("Home", Icons.Default.Home),
    NavItem("Activity", Icons.Default.List),
    NavItem("My Garage", Icons.Default.Add),
    NavItem("Showrooms", Icons.Default.ShoppingCart),
    NavItem("Explore", Icons.Default.Search)
)

// ── Root screen ─────────────────────────────────────────────────────────────

@Composable
fun StaticHomeScreen() {
    var activeTab by remember { mutableStateOf("All") }
    var selectedNav by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            StaticBottomNav(selected = selectedNav, onSelect = { selectedNav = it })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Sticky blue header ────────────────────────────────────────
            Box(modifier = Modifier.background(Cars24Primary)) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {

                    // Location + Avatar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LocationOn, "Location", tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                            Spacer(Modifier.width(8.dp))
                            Text("Bangalore", color = Color.White, fontWeight = FontWeight.SemiBold)
                            Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(14.dp))
                        }
                        StaticAvatar(fallbackText = "DD", size = 36.dp)
                    }

                    Spacer(Modifier.height(12.dp))

                    // Search bar
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, null, tint = Color.White.copy(alpha = 0.6f), modifier = Modifier.size(15.dp))
                            Spacer(Modifier.width(10.dp))
                            Text("Search Alto", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Tab row
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        listOf("All", "Buy used car", "Sell car", "Loans", "More").forEach { tab ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable { activeTab = tab }
                            ) {
                                Text(
                                    text = tab,
                                    color = if (activeTab == tab) Color.White else Color.White.copy(alpha = 0.6f),
                                    fontWeight = if (activeTab == tab) FontWeight.SemiBold else FontWeight.Normal,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .height(2.dp)
                                        .width(20.dp)
                                        .background(if (activeTab == tab) Color.White else Color.Transparent)
                                )
                            }
                        }
                    }
                }
            }

            // ── Scrollable content ────────────────────────────────────────
            Column(modifier = Modifier.background(Color.White)) {

                StaticFeaturedBanner()

                StaticSection(title = "Buy car", badge = "Up to ₹80,000 off", badgeColor = Color(0xFFE31837)) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(buyCards) { card ->
                            StaticActionCard(title = card.title, backgroundColor = card.bg, imageUrl = card.imageUrl)
                        }
                    }
                }

                StaticSection(title = "Sell your car") {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(sellCards) { card ->
                            StaticActionCard(title = card.title, backgroundColor = card.bg, imageUrl = card.imageUrl)
                        }
                    }
                }

                StaticSection(title = "Used cars you'll love", viewAll = true) {
                    StaticCarListingCards()
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

// ── Sub-composables ──────────────────────────────────────────────────────────

@Composable
private fun StaticAvatar(fallbackText: String, size: Dp = 40.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = fallbackText,
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            color = Cars24Primary
        )
    }
}

@Composable
private fun StaticSection(
    title: String,
    badge: String? = null,
    badgeColor: Color = Cars24Primary,
    viewAll: Boolean = false,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                if (badge != null) {
                    Spacer(Modifier.width(8.dp))
                    Surface(color = badgeColor, shape = RoundedCornerShape(12.dp)) {
                        Text(
                            text = badge,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            if (viewAll) {
                Text("View all", color = Cars24Primary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(8.dp))
        content()
    }
}

@Composable
private fun StaticFeaturedBanner() {
    Box(
        modifier = Modifier
            .background(Cars24Primary)
            .padding(bottom = 20.dp)
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1574023240744-64c47c8c0676?w=400",
                        contentDescription = "Car",
                        modifier = Modifier
                            .size(112.dp, 80.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text("BUY USED CAR", color = Cars24Primary, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "High demand for the car you viewed · 2024 Thar Roxx",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            lineHeight = 17.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Inspected · Certified · Best Price", color = Color.Gray, fontSize = 11.sp)
                        Spacer(Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            listOf("33,613 km", "Diesel", "Auto").forEach { spec ->
                                Surface(
                                    color = Color(0xFFF0F0F0),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(spec, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFEEEEEE))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFFF0FFF4),
                        border = BorderStroke(1.dp, Color(0xFFC6F6D5)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "₹ 18.79 Lakh",
                            color = Color(0xFF2F855A),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Book FREE test drive", color = Cars24Primary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Spacer(Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Cars24Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ArrowForward, null, tint = Color.White, modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StaticActionCard(title: String, backgroundColor: Color, imageUrl: String) {
    Box(
        modifier = Modifier
            .size(144.dp, 100.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(backgroundColor)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.35f
        )
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopStart)
                .width(100.dp)
        )
    }
}

@Composable
private fun StaticCarListingCards() {
    val cars = listOf(
        Triple("2024 Mahindra Thar Roxx", "₹18.79 lakh", "https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=400"),
        Triple("Honda City ZX 2020", "₹8.50 lakh", "https://images.unsplash.com/photo-1550355291-bbee04a2023f?w=400"),
        Triple("Hyundai Creta S 2022", "₹12.25 lakh", "https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=400")
    )
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        cars.forEach { (name, price, url) ->
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    AsyncImage(
                        model = url,
                        contentDescription = name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(price, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun StaticBottomNav(selected: String, onSelect: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = selected == item.label,
                onClick = { onSelect(item.label) },
                icon = { Icon(item.icon, item.label, modifier = Modifier.size(22.dp)) },
                label = { Text(item.label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Cars24Primary,
                    selectedTextColor = Cars24Primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
