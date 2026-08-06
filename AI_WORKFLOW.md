# AI_WORKFLOW.md — AI-Assisted Development Evidence

## Tool Stack

| Tool | Role |
|---|---|
| **Firebender (Claude Sonnet 4.6)** | Primary coding agent — architecture, code generation, debugging, file edits |
| **Android Studio** | IDE, Compose previews, build variant switching, APK signing |
| **adb / logcat** | Runtime verification — timing logs, install, gfxinfo |
| **Gradle** | Build verification after every AI edit (`./gradlew assembleDebug`) |

### How the AI was briefed

No separate rules/context file was written. Instead, the AI was given the full
assignment spec as a single prompt at the start of each major phase:

- **Phase 1:** "use proper MVVM architecture" — AI explored the project structure first, then proposed the layer breakdown
- **Phase 2:** Full spec paste (Part 1–4 of the assignment) — AI asked 3 clarifying questions before writing any code (ViewModel loading strategy, props parsing approach, visual fidelity target)
- **Phase 3:** "step by step" instruction — AI maintained a `TodoWrite` task list visible throughout, completing one task before starting the next

The most effective briefing pattern was: **spec paste → AI clarifying questions → explicit answers → implementation**. Giving the AI too much freedom without answering its questions produced the repository scaffold failure (Story 3 below).

---

## Three Prompt → Outcome Stories

---

### Story 1 — Build failure: the two-step diagnosis

**The prompt:**
```
My code gets an exception
Caused by: java.lang.IllegalArgumentException: Cannot add extension 'kotlin'
as it already exists
```

**What AI produced (attempt 1):**
AI diagnosed the cause as `kotlin.kapt` re-applying the Kotlin Android plugin
internally — a known conflict. It migrated from kapt to KSP:
- Added `ksp = "2.2.10-2.0.2"` to `libs.versions.toml`
- Replaced `alias(libs.plugins.kotlin.kapt)` with `alias(libs.plugins.ksp)`
- Replaced `kapt(libs.hilt.compiler)` with `ksp(libs.hilt.compiler)`

**Build result:** Still failed — different error:
```
Cannot add extension 'kotlin' as it already exists
  at com.android.build.gradle.internal.plugins.AppPlugin
```

**What I rejected and why:**
The KSP migration was correct but incomplete. AI had identified the wrong
primary cause — it was not kapt that was doubling the Kotlin plugin. AGP 9.x
registers the Kotlin Android extension internally when it detects a Kotlin
project. The explicit `alias(libs.plugins.kotlin.android)` in `app/build.gradle.kts`
was then a second registration — the real duplicate.

**What AI produced (attempt 2):**
After reading the full Gradle error output (which named `AppPlugin` not `KaptPlugin`),
AI removed `alias(libs.plugins.kotlin.android)` from the app-level plugins block
entirely:

```kotlin
// Before
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)   // ← this was the duplicate
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

// After
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}
```

**Build result:** `BUILD SUCCESSFUL`

**Lesson:** AI's first diagnosis was a reasonable hypothesis but wrong. The
verification loop (read Gradle error → re-diagnose → fix) is what caught it.
Without running the build after each change, the first fix would have been
accepted as correct.

---

### Story 2 — Schema design: accepting AI's recommendation then overriding one decision

**The prompt:**
```
[pasted data.json with snake_case schema]
is this data.json schema better than the home_screen.json?
```

**What AI produced:**
A structured comparison across 8 dimensions recommending `data.json` as superior:
- snake_case naming (REST standard vs Kotlin convention leaking into JSON)
- `specs` instead of `chips` (data-semantic vs UI-semantic naming)
- Cars nested inside tabs (vs fragile `carsByTab: Map<String, List<Car>>`)
- Generic `actions: []` on showrooms (vs hardcoded `callAction`/`viewAction`)
- `target_ids` list on filter action (vs comma-separated string)
- `interval_ms` + `auto_scroll` (vs single `autoScrollIntervalMs`)

**What I accepted:** All 6 structural improvements. The migration was executed
across all 9 Props classes, the composables, and the JSON file.

**What I rejected (one decision):**
AI proposed leaving `textColor` off `BrandFooterProps` in the new schema
(data.json only had `bg_color`). I kept `text_color` as a field in the
implementation because a footer where the text colour is hardcoded in code
violates the SDUI principle — the server should control both. AI had
correctly described it as a gap but then didn't add it to the migration.
I caught this during the composable rewrite.

**Outcome:** The schema migration ran cleanly. `BUILD SUCCESSFUL` on first
attempt after all 9 Props classes were updated — no runtime crashes because
every field had a Kotlin default value so missing JSON keys are safe.

---

### Story 3 — Image rendering: AI's `BlendMode.Multiply` failure

**The prompt:**
```
in the banners of buy cars the images remove the white background
only keep the car
```

**What AI produced:**
```kotlin
AsyncImage(
    model = card.imageUrl,
    modifier = Modifier.graphicsLayer {
        compositingStrategy = CompositingStrategy.Offscreen
        renderEffect = null
    }.drawWithContent {
        drawContent()
        drawRect(color = Color.Transparent, blendMode = BlendMode.Multiply)
    }
)
```

AI explained: "White pixels (1,1,1) × card background = background colour →
they vanish. Dark car pixels × background = darker tint → car stays visible."

**What I rejected and why:**
The output was correct in theory but wrong in practice. The card backgrounds
are deep indigo blue (`#3535D4`) and dark green (`#1E7C52`). Multiplying
dark car pixels against these dark backgrounds produced near-black cars —
impossible to see. User feedback: **"make the cars brighter cant see them properly"**

The fundamental problem: `BlendMode.Multiply` only works for white-background
removal when the card background is itself light/white. On dark backgrounds
it darkens everything uniformly.

**What was done instead:**
```kotlin
AsyncImage(
    model = card.imageUrl,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
        .clip(RoundedCornerShape(bottomEnd = 12.dp))
)
```

`ContentScale.Crop` zooms the image so the car body fills the frame —
the white margins are cropped away naturally. No blend mode, no darkness,
full brightness. This was the correct solution from the start.

**Lesson:** AI's `BlendMode.Multiply` answer was technically sophisticated
and correct in isolation — but it failed to account for the actual background
colours in the design. A simple check ("what colour is the card background?")
before proposing the solution would have avoided the failure. Device testing
caught it; code review alone would not have.

---

## One Major AI Failure — The Repository Scaffold

**What happened:**
Early in the project, before the full MVVM spec was clarified, AI was asked:
```
use proper mvvm architecture
```

AI immediately scaffolded a full enterprise data layer:

```
data/
├── source/
│   └── SduiDataSource.kt       ← interface + impl
├── repository/
│   └── SduiRepository.kt       ← interface
│   └── SduiRepositoryImpl.kt   ← implementation
```

And wired it into an `AppModule` with Hilt bindings for repository injection
into the ViewModel.

**Why this was wrong:**
The assignment spec explicitly stated:
> "HomeViewModel loads/parses JSON directly via @ApplicationContext — no repository layer"

The Repository pattern is correct for production apps with multiple data sources
(network + cache + local DB). For this assignment — a single bundled JSON file
with no caching — it adds indirection with zero benefit. It also violated the
spec's stated architecture.

**How it was caught:**
The spec was re-read carefully after the scaffold was generated. When asked
"ViewModel loads JSON directly — do you need a repository?", the AI confirmed
the scaffold was over-engineered for this use case.

**How it was fixed:**
All three scaffolded files were deleted:
```
deleted: data/source/SduiDataSource.kt
deleted: data/repository/SduiRepository.kt
deleted: data/repository/SduiRepositoryImpl.kt
```

`HomeViewModel` was rewritten to inject `@ApplicationContext` and `Gson` directly,
loading from `assets/data.json` on `Dispatchers.IO`.

**Root cause of the failure:**
AI defaulted to its training distribution ("proper MVVM" in Android almost always
means Repository pattern in open-source examples). It didn't ask whether a
repository was needed — it just added one. The fix was requiring AI to ask
clarifying questions before writing architecture code, not after.

---

## Verification Strategy for AI-Generated Code

Every piece of AI-generated code went through this checklist before being accepted:

### 1. Build verification (non-negotiable)
```bash
./gradlew assembleDebug
```
Run after every file change. A `BUILD SUCCESSFUL` is the minimum bar.
**Count of build failures caught this way during development: 6**

### 2. Read before write
AI was required to call `read_file` on every file before editing it.
This prevents stale-context edits where AI writes code based on a remembered
version of a file that has since changed.

### 3. Runtime verification
- `adb logcat -s SDUI_PERF` — confirmed timing numbers matched expectations
- Device install after every significant change — visual check for regressions
- Scroll test after layout changes — checked for jank or nested scroll crashes

### 4. Visual rejection loop
Several AI outputs were rejected purely on visual inspection after install:
- **Wrong colours** (red tabs instead of blue) — caught on device
- **Dark car images** (`BlendMode.Multiply` failure) — caught on device
- **Broken image URLs** (fabricated CDN paths) — caught when images didn't load
- **Missing bottom padding** (content hidden under nav bar) — caught on device

### 5. Spec cross-check
For architecture decisions (ViewModel loading directly, lazy props parsing,
`filter_sections` via `target_ids`), the output was compared against the
explicit requirement in the assignment spec before accepting.

### 6. What was NOT verified (honest gaps)
- **Unit tests** — `HomeViewModel` (JSON parsing, filter logic) and
  `SDUIRenderer` (component routing) have no automated tests. Manual device
  testing was the only runtime verification.
- **Edge cases in JSON** — malformed JSON, missing required fields, and
  empty component arrays were not explicitly tested with bad inputs.
- **Accessibility** — no content descriptions audit was done on AI-generated
  composables.
