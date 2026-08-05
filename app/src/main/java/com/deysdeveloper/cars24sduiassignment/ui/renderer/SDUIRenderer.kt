package com.deysdeveloper.cars24sduiassignment.ui.renderer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.deysdeveloper.cars24sduiassignment.data.model.Action
import com.deysdeveloper.cars24sduiassignment.data.model.Component
import com.deysdeveloper.cars24sduiassignment.data.model.ComponentType
import com.deysdeveloper.cars24sduiassignment.data.model.props.BrandFooterProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.CategoryTabBarProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.FeaturedCarCardProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.HeaderProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.PromoBannerCarouselProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.SectionCardRailProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.ServiceGridProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.ShowroomRailProps
import com.deysdeveloper.cars24sduiassignment.data.model.props.TabbedCarListingProps
import com.deysdeveloper.cars24sduiassignment.ui.components.BrandFooterComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.CategoryTabBarComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.FeaturedCarCardComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.HeaderComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.PromoBannerCarouselComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.SectionCardRailComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.ServiceGridComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.ShowroomRailComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.TabbedCarListingComponent
import com.deysdeveloper.cars24sduiassignment.ui.components.UnknownComponent
import com.google.gson.Gson

/**
 * The core SDUI rendering engine.
 *
 * Loops through [components] in a [LazyColumn], applies the active tab filter,
 * lazily parses each component's [props] into its typed Props class, then
 * delegates to the matching composable. Unknown types render as an empty [Box].
 *
 * Filtering rules:
 * - [activeFilter] == null → show all components ("All" tab selected).
 * - [ComponentType.HEADER] and [ComponentType.CATEGORY_TAB_BAR] are always visible.
 * - All other components are shown only if their [Component.id] is in [activeFilter].
 */
@Composable
fun SDUIRenderer(
    components: List<Component>,
    activeFilter: Set<String>?,
    gson: Gson,
    onAction: (Action) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    val visible = components.filter { component ->
        when {
            activeFilter == null -> true
            component.type == ComponentType.HEADER -> true
            component.type == ComponentType.CATEGORY_TAB_BAR -> true
            else -> component.id in activeFilter
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding(),
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        )
    ) {
        items(visible, key = { it.id }) { component ->
            RenderComponent(component = component, gson = gson, onAction = onAction)
        }
    }
}

/**
 * Dispatches a single [component] to its composable.
 * Props are parsed lazily here — each branch only deserialises the props
 * type it needs. The [else] branch ensures unknown types are always safe.
 */
@Composable
private fun RenderComponent(
    component: Component,
    gson: Gson,
    onAction: (Action) -> Unit
) {
    when (component.type) {
        ComponentType.HEADER -> HeaderComponent(
            props = gson.fromJson(component.props, HeaderProps::class.java),
            onAction = onAction
        )

        ComponentType.CATEGORY_TAB_BAR -> CategoryTabBarComponent(
            props = gson.fromJson(component.props, CategoryTabBarProps::class.java),
            onAction = onAction
        )

        ComponentType.FEATURED_CAR_CARD -> FeaturedCarCardComponent(
            props = gson.fromJson(component.props, FeaturedCarCardProps::class.java),
            onAction = onAction
        )

        ComponentType.SECTION_CARD_RAIL -> SectionCardRailComponent(
            props = gson.fromJson(component.props, SectionCardRailProps::class.java),
            onAction = onAction
        )

        ComponentType.SERVICE_GRID -> ServiceGridComponent(
            props = gson.fromJson(component.props, ServiceGridProps::class.java),
            onAction = onAction
        )

        ComponentType.TABBED_CAR_LISTING -> TabbedCarListingComponent(
            props = gson.fromJson(component.props, TabbedCarListingProps::class.java),
            onAction = onAction
        )

        ComponentType.SHOWROOM_RAIL -> ShowroomRailComponent(
            props = gson.fromJson(component.props, ShowroomRailProps::class.java),
            onAction = onAction
        )

        ComponentType.PROMO_BANNER_CAROUSEL -> PromoBannerCarouselComponent(
            props = gson.fromJson(component.props, PromoBannerCarouselProps::class.java),
            onAction = onAction
        )

        ComponentType.BRAND_FOOTER -> BrandFooterComponent(
            props = gson.fromJson(component.props, BrandFooterProps::class.java)
        )

        else -> UnknownComponent()
    }
}
