# COVERAGE.md — SDUI Schema Generalization Report

## Component Registry

This app ships **9 registered component types** + 1 universal fallback.
Every component is rendered by `SDUIRenderer` via a `when` block — adding a new
type requires exactly 4 files and zero changes to existing code.

| # | JSON `type` | Composable | UI Pattern |
|---|---|---|---|
| 1 | `header` | `HeaderComponent` | Sticky top bar — location selector, search pill, avatar |
| 2 | `category_tab_bar` | `CategoryTabBarComponent` | Horizontal tab strip with `filter_sections` action |
| 3 | `featured_car_card` | `FeaturedCarCardComponent` | Hero card — image, tag, specs chips, price, CTA button |
| 4 | `section_card_rail` | `SectionCardRailComponent` | Horizontal scroll rail of N image+label cards |
| 5 | `service_grid` | `ServiceGridComponent` | N-column grid of icon+label items (`columns` field) |
| 6 | `tabbed_car_listing` | `TabbedCarListingComponent` | Tab switcher → vertical list of car cards |
| 7 | `showroom_rail` | `ShowroomRailComponent` | Horizontal scroll — status badge, distance, N action buttons |
| 8 | `promo_banner_carousel` | `PromoBannerCarouselComponent` | Auto-scroll pager with dot indicators |
| 9 | `brand_footer` | `BrandFooterComponent` | Full-width coloured block, headline + subtext |
| — | _(any unknown)_ | `UnknownComponent` | Empty `Box` — safe no-op, never crashes |

---

## What the Schema Can Express (JSON-only changes)

### Lists & Rails
`section_card_rail` accepts any number of `cards` — from 1 to N. The same
composable renders the Buy rail (4 cards), Sell rail (3 cards), Loans rail,
and Trending rail. A new rail for "Electric Cars" or "Luxury" requires
**zero code changes** — just a new JSON component entry.

### Grids
`service_grid` exposes a `columns` field. Changing `"columns": 3` to
`"columns": 2` or `"columns": 4` reflows the grid with no code change.
Number of items is also unconstrained.

### Conditionals / Visibility
The `category_tab_bar` component drives section visibility via `filter_sections`
actions with `target_ids` arrays. The server controls which component IDs appear
under each tab — entire sections can be shown, hidden, or reordered by changing
which IDs are listed. This is the schema's primary conditional mechanism.

### Actions
Three action types are registered in `ActionHandler`:

| Action type | Trigger | Parameters |
|---|---|---|
| `navigate` | Tap any tappable element | `destination`: any registered route |
| `call` | Call button on showroom card | `phone`: number string |
| `filter_sections` | Tab bar tap | `target_ids`: list of component IDs to show |

Any component can carry any action — a banner, a card, a service grid item, or a
section rail card all accept an `action` object with the same shape.

### Styling Overrides (server-controlled)
| Field | Where | Effect |
|---|---|---|
| `bg_color` | `SectionCard`, `Banner`, `BrandFooterProps` | Hex background colour |
| `badge` / `badge_color` | `SectionCardRailProps` | Pill label on the rail title |
| `columns` | `ServiceGridProps` | Grid column count |
| `interval_ms` | `PromoBannerCarouselProps` | Auto-scroll speed |
| `auto_scroll` | `PromoBannerCarouselProps` | Enable / disable auto-scroll |
| `location_selectable` | `HeaderProps` | Whether location tap is active |
| `tag` | `FeaturedCarCardProps`, `Banner` | Label text overlay |
| `status` | `Showroom` | "open" / "closed" badge colour |
| `default_tab` | `TabbedCarListingProps` | Which tab is selected on load |
| `default_selected` | `CategoryTabBarProps` | Which filter tab starts active |

### Component Ordering & Count
`SDUIRenderer` renders components **in the order they appear in the JSON array**.
The server can:
- Reorder sections (move carousel above rail)
- Duplicate a component type (two rails back-to-back)
- Remove a section (omit it from the JSON)
- Insert a new unknown type (renders safely as empty box)

All with **zero client code changes**.

---

## Honest Coverage Claim

> **Given a new Cars24 screen, approximately 70% renders with JSON-only changes.
> The remaining 30% requires a new composable + Props class.**

### What renders today with JSON-only (the 70%)

| Screen / Feature | Coverage | Reason |
|---|---|---|
| New category tab layout (different tabs, different sections) | ✅ 100% | `category_tab_bar` + `target_ids` controls all filtering |
| New horizontal rail (any card count, any colour) | ✅ 100% | `section_card_rail` is fully generic |
| New promo campaign (different banners, timing, colours) | ✅ 100% | `promo_banner_carousel` props cover all variables |
| New service grid (2-col vs 3-col, any icon set) | ✅ 100% | `columns` field + `items` list |
| New featured hero car (any car, any specs) | ✅ 100% | `featured_car_card` props cover all variables |
| Different header location / search hint / avatar | ✅ 100% | `header` props |
| New showroom listing (any city, any showrooms) | ✅ 100% | `showroom_rail` props |
| New tab categories in used car listing | ✅ 100% | `tabbed_car_listing` tabs are data-driven |
| New footer branding (colour, text) | ✅ 100% | `brand_footer` bg_color + headline |
| A/B test: swap rail order | ✅ 100% | JSON array order |
| Hide a section for a user segment | ✅ 100% | Omit from JSON |
| Add a call-to-action to any card | ✅ 100% | Every item has an optional `action` field |

### What needs new client code (the 30%)

| Pattern | Why it needs code | Estimated effort |
|---|---|---|
| **Search results screen** | Infinite scroll, pagination, filter chips, sort bar | ~1 day |
| **Car detail page** | Gallery pager, spec table, loan calculator, sticky CTA | ~1 day |
| **Rating / review component** | Star widget, review list, submit form | ~4 hours |
| **Map / showroom locator** | Google Maps SDK embed, location permission | ~4 hours |
| **Form inputs** (loan apply, sell inquiry) | TextFields, validation, submission state | ~4 hours |
| **Video player component** | ExoPlayer embed, playback controls | ~4 hours |
| **Countdown timer** (limited offer) | Stateful timer, animation | ~2 hours |
| **Bottom sheet / modal** | Triggered by action, overlays current screen | ~2 hours |
| **Skeleton loading per component** | Per-type shimmer instead of global spinner | ~3 hours |
| **Deep-link navigation** (external) | `NavDeepLink` registration per route | ~2 hours |

---

## Adding a New Component — Step-by-Step

The schema is designed so that adding a new component type touches exactly **4 files**
and **zero existing files**:

```
Step 1 — Data class (new file)
  data/model/props/NewComponentProps.kt

Step 2 — Composable (new file)
  ui/components/NewComponent.kt

Step 3 — Register in renderer (1 line in existing file)
  ui/renderer/SDUIRenderer.kt
    ComponentType.NEW_TYPE -> NewComponent(
        props = gson.fromJson(component.props, NewComponentProps::class.java),
        onAction = onAction
    )

Step 4 — Add type constant (1 line in existing file)
  data/model/ScreenData.kt
    const val NEW_TYPE = "new_type"
```

**No changes to:** `HomeViewModel`, `ActionHandler`, `AppNavigation`,
`HomeUiState`, `AppModule`, `MainActivity`, or any other existing component.

---

## Live Interview Scenario — "Write JSON for a screen you didn't build"

### How to approach it

Given any new Cars24 screen in the interview, the process is:

1. **Identify which components already exist** — map each section to a type from
   the registry above.
2. **Write the JSON skeleton** — `screen_id`, `screen_version`, `components: []`
3. **Fill props per component** — use the field tables above as a reference.
4. **Identify gaps** — sections that don't map to any existing type; name the new
   `type` string and describe what props it would need.
5. **Add the new composable live** — 4-file process above, takes ~15 minutes.

### Expected outcome for a typical Cars24 screen

| Section type encountered | Likely mapping | Code needed? |
|---|---|---|
| Top navigation bar | `header` | No |
| Category filter row | `category_tab_bar` | No |
| Hero banner / featured item | `featured_car_card` | No |
| Any horizontal card scroll | `section_card_rail` | No |
| Icon grid (services, features) | `service_grid` | No |
| Tabbed content | `tabbed_car_listing` | No |
| Location-based cards | `showroom_rail` | No |
| Image carousel | `promo_banner_carousel` | No |
| Branding footer | `brand_footer` | No |
| Rating widget | ❌ new component | ~4 hours |
| Form / input fields | ❌ new component | ~4 hours |
| Map embed | ❌ new component | ~4 hours |

---

## Schema Design Principles

### 1. `props` as raw `JsonObject` until render time
The `Component.props` field stays as `JsonObject` at the model layer.
Each branch in `RenderComponent` calls `gson.fromJson(component.props, XProps::class.java)`
only for the type being rendered. Off-screen components never pay any parse cost.

### 2. All fields have safe defaults
Every Props data class field has a Kotlin default value. A missing JSON key
never throws — Gson assigns the default. This means:
- The server can omit optional fields safely
- Old client builds handle new optional fields from newer server JSON
- Malformed JSON for one component doesn't crash others

### 3. Actions are uniform across all components
Every tappable element — banner, card, grid item, CTA button — passes an
`Action` object to the same `onAction: (Action) -> Unit` lambda. No component
has any direct awareness of navigation or context. Adding a new action type
(`share`, `add_to_wishlist`, `open_chat`) requires one new `when` branch in
`ActionHandler` only.

### 4. Unknown types degrade gracefully
`else -> UnknownComponent()` renders an empty `Box`. The server can deploy new
component types before the client ships the matching composable — older app
versions show nothing for that section rather than crashing. This is the
forward-compatibility guarantee.
