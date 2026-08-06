# SDUI Implementation

An Android app demonstrating **Server-Driven UI (SDUI)** built with Jetpack Compose and Hilt. The app lets you compare a fully JSON-driven screen side-by-side with a traditional hardcoded screen.

---

## Screen Recording

[▶️ Watch the demo (Google Drive)](https://drive.google.com/file/d/1cJC9rBatXVmI6ItfogDV0xsWp07urb1W/view?usp=sharing)

Covers:
- Page rendering from `data.json` — cold launch to fully rendered screen
- Category tab filtering (Buy / Sell / Loans) — sections update with zero code
- Loan tenure selector + bottom sheet — EMI recalculates live on tenure change
- Unknown component fallback (`new_feature_not_yet_built`) — renders safely as empty
- Live JSON edit — title and colour changed in `data.json`, re-run, page updates with no Kotlin changes

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

## Schema Design Rationale

### Why `props` stays as raw `JsonObject`
Each component's `props` field is kept as a `JsonObject` at the model layer and
only deserialised into a typed `XProps` class at render time — lazily, per
component, only when it's about to be laid out. This means:
- Off-screen components pay zero parse cost
- Adding a new field to a Props class is backwards-compatible — old JSON without
  that field gets the Kotlin default value, never a crash
- The ViewModel only parses `ScreenData` (the envelope) on the IO thread; the
  per-component props cost is deferred to composition time on the main thread

### Why snake_case JSON fields
All `@SerializedName` annotations use snake_case (`image_url`, `bg_color`,
`interval_ms`). This matches the REST API standard used by every real backend.
Using camelCase in JSON would be a Kotlin convention leaking into the schema.

### Why cars are nested inside tabs (not a flat `carsByTab` map)
```json
// ❌ Fragile — key must exactly match tab id
"carsByTab": { "recently_viewed": [...], "hot_deals": [...] }

// ✅ Self-contained — no implicit key contract
"tabs": [
  { "id": "recently_viewed", "label": "Recently Viewed", "cars": [...] },
  { "id": "hot_deals",       "label": "Hot Deals",       "cars": [...] }
]
```
With the nested approach, a tab with no matching key is simply an empty list —
the screen renders safely. With the map approach, a typo in a tab ID silently
shows no cars with no error.

### Why `actions: []` array on showrooms (not `callAction` / `viewAction`)
Hardcoding two named action fields means the composable is forever limited to
exactly two buttons. A generic `actions` list lets the server add a third button
("Get Directions", "WhatsApp") with zero client code changes.

---

## Versioning Story

`ScreenData` carries a `screen_version` field:
```json
{ "screen_id": "home", "screen_version": "1.0.0", "components": [...] }
```

**How this would work in production:**

1. Client caches the last-received `(screen_id, screen_version, components)` in
   Room / SharedPreferences on first load.
2. On next open, client sends its cached `screen_version` to the server.
3. Server compares versions:
   - **Same version** → returns `304 Not Modified` → client renders cached JSON instantly (0ms load time)
   - **New version** → returns full new JSON → client parses, renders, updates cache
4. Unknown component types in the new JSON degrade gracefully via `UnknownComponent`
   — older app builds never crash on new server-added component types.

In this project the JSON is bundled in assets, so `screen_version` is not actively
used. The field is present in the schema so the versioning contract is demonstrable
without a backend.

---

## Trade-offs

### What SDUI gives you
| Benefit | Example in this project |
|---|---|
| Zero-code screen changes | Reorder sections by changing JSON array order |
| Server-controlled A/B testing | Return different component list per user segment |
| Kill switch for broken sections | Omit a component ID from JSON |
| Gradual rollout | Serve new component type to 10% of users |
| Forward compatibility | Unknown types render as empty — old builds survive |

### What SDUI costs you
| Cost | Mitigation in this project |
|---|---|
| Extra parsing step (Gson) | IO thread + lazy props parse — main thread never blocked |
| Harder to debug (no compile-time safety on JSON) | Typed Props classes + safe defaults eliminate runtime crashes |
| Component proliferation over time | 4-file pattern keeps each addition isolated |
| Schema versioning complexity | `screen_version` field provides the hook |
| Images always async | Coil handles this — first frame renders before images appear |

### SDUI vs Static — the bottom line
The static screen (`StaticHomeScreen`) composes its entire view tree eagerly on
the first frame — every section exists in memory whether visible or not. The SDUI
screen's `LazyColumn` only composes visible components. On benchmarks, the SDUI
screen was **22ms faster** on cold-start first frame (130ms vs 152ms) despite the
JSON parsing cost — because the `LazyColumn` initial composition is cheaper than
the static screen's full eager layout. See `PERF.md` for full numbers.

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
