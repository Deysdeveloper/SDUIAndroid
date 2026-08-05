# SDUI Implementation

An Android app demonstrating **Server-Driven UI (SDUI)** built with Jetpack Compose and Hilt. The app lets you compare a fully JSON-driven screen side-by-side with a traditional hardcoded screen.

---

## How to Run

1. Clone the repo and open in **Android Studio Meerkat** or later
2. Let Gradle sync complete
3. Run on any device/emulator with **API 26+**
4. On launch, tap **SDUI** or **Static** to open the respective screen

---

## Architecture

```
MVVM  +  Unidirectional Data Flow  +  Server-Driven UI
```

```
home_screen.json (assets)
        │
        ▼ (IO thread, Gson)
  HomeViewModel
  ├── uiState: StateFlow<HomeUiState>   (Loading / Success / Error)
  └── activeFilter: StateFlow<Set<String>?>  (null = show all)
        │
        ▼ (collectAsStateWithLifecycle)
  HomeScreen
  ├── LoadingContent
  ├── ErrorContent  (+Retry)
  └── SDUIRenderer  ──► LazyColumn
            │
            ▼ (when block, lazy props parse per type)
       Component Composables
            │
            ▼ onAction(Action)
       handleAction()
       ├── navigate  → NavController
       ├── call      → Intent.ACTION_DIAL
       └── filter_sections → HomeViewModel.filterSections()
```

---

## Project Structure

```
app/src/main/
├── assets/
│   └── home_screen.json          # Single source of truth for the SDUI screen
│
└── java/.../
    ├── App.kt                    # @HiltAndroidApp
    ├── MainActivity.kt           # @AndroidEntryPoint, edge-to-edge
    │
    ├── data/
    │   └── model/
    │       ├── ScreenData.kt     # ScreenData, Component, ComponentType
    │       ├── Action.kt         # Action, ActionType
    │       └── props/            # One typed Props class per component type
    │           ├── HeaderProps.kt
    │           ├── CategoryTabBarProps.kt
    │           ├── FeaturedCarCardProps.kt
    │           ├── SectionCardRailProps.kt
    │           ├── ServiceGridProps.kt
    │           ├── TabbedCarListingProps.kt
    │           ├── ShowroomRailProps.kt
    │           ├── PromoBannerCarouselProps.kt
    │           └── BrandFooterProps.kt
    │
    ├── di/
    │   └── AppModule.kt          # Provides Gson singleton
    │
    ├── navigation/
    │   └── AppNavigation.kt      # Routes, NavHost, navigateSafe()
    │
    └── ui/
        ├── action/
        │   └── ActionHandler.kt  # Central action dispatcher
        ├── components/           # One file per SDUI component type
        │   ├── HeaderComponent.kt
        │   ├── CategoryTabBarComponent.kt
        │   ├── FeaturedCarCardComponent.kt
        │   ├── SectionCardRailComponent.kt
        │   ├── ServiceGridComponent.kt
        │   ├── TabbedCarListingComponent.kt
        │   ├── ShowroomRailComponent.kt
        │   ├── PromoBannerCarouselComponent.kt
        │   ├── BrandFooterComponent.kt
        │   └── UnknownComponent.kt
        ├── renderer/
        │   └── SDUIRenderer.kt   # LazyColumn + filter logic + when block
        ├── screen/
        │   ├── ChooserScreen.kt  # Entry point — pick SDUI or Static
        │   ├── HomeScreen.kt     # SDUI screen (Scaffold + bottom nav)
        │   └── StaticHomeScreen.kt  # Hardcoded baseline for comparison
        ├── state/
        │   └── HomeUiState.kt    # Loading | Success | Error
        └── viewmodel/
            └── HomeViewModel.kt  # @HiltViewModel, loads JSON, manages filter
```

---

## SDUI Component Types

| JSON `type` | Composable | Description |
|---|---|---|
| `header` | `HeaderComponent` | Location selector, search bar, avatar |
| `category_tab_bar` | `CategoryTabBarComponent` | Scrollable tab row with filter action |
| `featured_car_card` | `FeaturedCarCardComponent` | Hero card with image, specs, CTA |
| `section_card_rail` | `SectionCardRailComponent` | Horizontal card rail (Buy / Sell / Loans / Trending) |
| `service_grid` | `ServiceGridComponent` | 3-column fixed grid of service items |
| `tabbed_car_listing` | `TabbedCarListingComponent` | Tab switcher + car cards with favourite toggle |
| `showroom_rail` | `ShowroomRailComponent` | Showroom cards with dual Call / View CTAs |
| `promo_banner_carousel` | `PromoBannerCarouselComponent` | Auto-scrolling full-width banner pager |
| `brand_footer` | `BrandFooterComponent` | Full-width coloured footer with tagline |
| *(anything else)* | `UnknownComponent` | Empty `Box` — graceful no-op for unknown types |

---

## Action Types

Actions are embedded in the JSON and dispatched through a single `handleAction()` function — no composable ever touches `NavController` or `Intent` directly.

| `type` | Params | Behaviour |
|---|---|---|
| `navigate` | `destination`, optional `params` | `navController.navigateSafe(destination)` — invalid routes are silently ignored |
| `call` | `phone` | Fires `Intent.ACTION_DIAL` — no `CALL_PHONE` permission required |
| `filter_sections` | `sections` (comma-separated IDs) | Updates `HomeViewModel.activeFilter`; blank string resets to show all |

---

## How to Add a New SDUI Component

1. **JSON** — add a new object to `home_screen.json` with a unique `type` string, `id`, and `props`
2. **Props data class** — create `data/model/props/YourComponentProps.kt` with `@SerializedName` fields matching the JSON keys
3. **Composable** — create `ui/components/YourComponent.kt` accepting `(props: YourComponentProps, onAction: (Action) -> Unit)`
4. **Register** — add a branch to the `when` block in `SDUIRenderer.kt`:
   ```kotlin
   ComponentType.YOUR_TYPE -> {
       val props = gson.fromJson(component.props, YourComponentProps::class.java)
       item { YourComponent(props, onAction) }
   }
   ```
5. Add the type constant to `ComponentType` in `ScreenData.kt`

That's it — **no changes** to `HomeViewModel`, `HomeScreen`, `ActionHandler`, or `AppNavigation`.

---

## Tech Stack

| Library | Version | Purpose |
|---|---|---|
| AGP | 9.3.1 | Android Gradle Plugin |
| Kotlin | 2.2.10 | Language |
| KSP | 2.2.10-2.0.2 | Annotation processor (replaces kapt) |
| Jetpack Compose BOM | 2026.02.01 | All Compose libraries |
| Hilt | 2.59 | Dependency injection |
| Gson | 2.14.0 | JSON parsing |
| Coil | 2.7.0 | Async image loading |
| Navigation Compose | 2.9.8 | Screen navigation |
| Lifecycle Runtime Compose | 2.8.7 | `collectAsStateWithLifecycle` |
