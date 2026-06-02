☀️

**SunGuard**

Design System & UI Guidelines

*Wear OS 4 + Android Companion  |  v1.1  |  May 2026*

---

# 1. Design Philosophy

SunGuard is a tool, not a destination. Users open it for seconds, not minutes. Every design decision is filtered through three principles:

| Invisible When Idle | The app should feel like it isn't there until it's needed. No dashboards to manage, no feeds to scroll, no badges demanding attention. |
| :---- | :---- |
| **One Tap, Done** | Any protection action (logging, dismissing a reminder) must be completable in a single tap from any surface — watch face, tile, notification. |
| **Sun is the Hero** | The sun is not a threat icon. It is warm, friendly, and the central character of the brand. The app should feel like sunlight — warm, clear, energising. |

These principles override aesthetic preference. If a design element is beautiful but slows the user down by one second, cut it.

---

# 2. Colour Palette

The palette is derived entirely from sunlight and sky. All UI elements render as orange (`#E2852E`) on top of the gradient background. There is no dark mode in v1.0. There are no neutral greys, no white cards, no shadows.

## 2.1 Primary Brand Colours

| Swatch | Hex | Role |
| :---- | :---- | :---- |
| 🟠 | `#E2852E` | **Sun Orange** — all interactive elements, icons, labels, and text on both platforms |
| 🟡 | `#F5C857` | **Sun Yellow** — gradient midpoint (phone), medium UV badge background |
| 🌕 | `#FFEE91` | **Sun Pale** — gradient top (phone), low UV badge background |
| 🔵 | `#ABE0F0` | **Sky Blue** — gradient bottom (phone), tile background (watch) |

## 2.2 Background Treatment — THE MOST CRITICAL RULE

**The background is a full-screen linear gradient on both phone and watch companion screens.**  
There are no solid background colours, no white screens, no card surfaces, and no dark backgrounds on the phone.

| Platform | Gradient | Direction |
| :---- | :---- | :---- |
| **Phone** | `#FFEE91` → `#F5C857` → `#ABE0F0` | Top to bottom (warm yellow at top, sky blue at bottom) |
| **Watch** | `#F5C857` → `#ABE0F0` | Top to bottom (compressed; same warm-to-cool direction) |

- The gradient fills 100% of the screen edge-to-edge, including behind the status bar and navigation bar.
- Do NOT add any container, card, or surface on top of this gradient. Every element floats directly on it.
- The phone screen has a **rounded rectangle frame** (corner radius ≈ 28dp) — this is the device form factor, not an app-level design element. Do not add an inner card to simulate this.

## 2.3 Foreground Colour — Single Colour Rule

**Every foreground element (icons, text, button strokes, dividers) uses exactly one colour: `#E2852E` (Sun Orange).**

There is no secondary text colour, no grey, no white text, no black text. The entire UI is monochromatic orange on gradient.

| Element | Colour |
| :---- | :---- |
| All body text | `#E2852E` |
| All label text | `#E2852E` |
| All section headers | `#E2852E` |
| All icons | `#E2852E` |
| Apply button stroke | `#E2852E` |
| Apply button label | `#E2852E` |
| Log entry timestamps | `#E2852E` |
| Footer text | `#E2852E` |

## 2.4 UV Index Colour Map (Badge Only)

UV colour is applied only to the UV badge background when a dedicated badge is shown. Outside the badge, the single-colour rule above applies.

| UV Level | Badge Background | Badge Text |
| :---- | :---- | :---- |
| UV 0–2 (Low) | `#FFEE91` | `#E2852E` |
| UV 3–5 (Moderate) | `#F5C857` | `#E2852E` |
| UV 6–7 (High) | `#E2852E` | `#FFFFFF` |
| UV 8–10 (Very High) | `#E2852E` | `#FFFFFF` + pulsing outer ring |
| UV 11+ (Extreme) | `#C0392B` | `#FFFFFF` + warning icon |

---

# 3. Typography

SunGuard uses a single typeface family. Simplicity in typography = faster reading in glance contexts.

## 3.1 Typeface

| Platform | Typeface | Notes |
| :---- | :---- | :---- |
| Watch Primary | Google Sans (system) | Wear OS system font — always available |
| Phone Primary | Google Sans / Roboto | Android system font |
| Log timestamps | Roboto Mono | Application log timestamps only |

## 3.2 Type Scale — Phone (matching reference image)

| Element | Size / Weight | Style | Notes |
| :---- | :---- | :---- | :---- |
| **Hero Icon** | 96–120dp artwork | n/a | Large flower/sun SVG, centred, `#E2852E` filled |
| **Apply Button Label** | 16sp / Regular | Sentence case | "Apply" — not all-caps in this design |
| **Stats Label (row)** | 11sp / Regular | Sentence case | e.g. "Exposure", "Recommended SPF", "Last Applied" |
| **Stats Value** | 13sp / Medium | — | e.g. "High", "SPF 30", "2:00" below each icon |
| **Section Header** | 18sp / Regular | Script/italic cursive | "Application Log" — rendered in a flowing handwritten style |
| **Log Entry** | 13sp / Regular | — | Plain timestamp list, e.g. "11:00", "10:34", "11:00", "4:48", "5:42" |
| **Footer Text** | 11sp / Italic | Centred | Motivational tagline at very bottom |

> **Script font note:** The "Application Log" section header uses a cursive/script style. Use a system serif italic or embed a lightweight script font (e.g. Dancing Script or Pacifico from Google Fonts). Do not use Google Sans bold for this element.

## 3.3 Type Scale — Watch

| Element | Size / Weight | Notes |
| :---- | :---- | :---- |
| Apply Button Label | 18sp / Medium | Centred in pill |
| UV Number | 36sp / Bold | Centred in compact badge |
| UV Level Label | 12sp / Regular | Below UV number |
| SPF Value | 22sp / Bold | Prefixed "SPF" in 12sp |
| Log Entry | 12sp / Regular | Roboto Mono for time |
| Tile UV Number | 18sp / Bold | Compact tile viewport |
| Complication | 11sp / Regular | "UV 7" format; short text only |

---

# 4. Spacing & Layout Grid

Both platforms use an 8dp base grid. No value should be an odd number or non-multiple of 4dp.

## 4.1 Phone Layout (matching reference image)

The phone screen is a single scrollable column. There are NO cards, NO dividers between sections, NO horizontal rules. Content floats on the gradient.

| Property | Value | Notes |
| :---- | :---- | :---- |
| **Screen horizontal padding** | 24dp | Left and right |
| **Hero icon size** | 96–120dp | Centred; top of screen, ~20% from top |
| **Icon to Apply button gap** | 16dp | |
| **Apply button height** | 40dp | Shorter than spec v1.0 — compact pill shape |
| **Apply button width** | 50% of screen width, centred | Not full-width |
| **Apply button style** | Outlined pill — stroke only, no fill | `#E2852E` 1.5dp stroke; background is transparent (gradient shows through) |
| **Apply button corner radius** | 20dp (pill) | height / 2 |
| **Button to stats row gap** | 28dp | |
| **Stats row** | Three equally-spaced columns | Full width, centre-aligned content in each column |
| **Stats icon size** | 28dp | Each icon `#E2852E` |
| **Stats value text** | 13sp, below icon | Small label |
| **Stats to section header gap** | 32dp | |
| **Section header ("Application Log")** | Centred | Script/cursive style |
| **Log list item height** | 28dp | Dense list; timestamps only, left-centred |
| **Log list horizontal indent** | 40% of screen (centred block) | Timestamps appear centred-ish, not full width |
| **Section header to log gap** | 12dp | |
| **Footer padding-top** | 32dp | |
| **Footer padding-bottom** | 24dp | Above home indicator |

## 4.2 Watch Layout

| Property | Value | Notes |
| :---- | :---- | :---- |
| Screen padding | 8dp all sides | Galaxy Watch 8: 396×396px circular |
| Section gap | 16dp | |
| Button height (Apply) | 64dp | Full width minus 2×8dp padding |
| Button corner radius | 32dp (pill) | |
| UV badge size | 72×72dp | Centred; circular |
| Log entry row height | 36dp | |
| Footer padding-top | 20dp | |

---

# 5. Component Specifications

## 5.1 Hero Icon (Phone — top of screen)

- **Shape:** Flower / stylised sun — 6 rounded petals, centre circle
- **Size:** 96–120dp, centred horizontally
- **Style:** Filled flat shape, `#E2852E`
- **No outline stroke**, no shadow, no glow
- **Not a button** — decorative / brand element only
- This icon replaces the UV badge as the primary visual hero on the phone main screen

## 5.2 Apply Button

- **Style: Outlined pill — stroke only. The background is fully transparent.**
- Stroke: 1.5dp solid `#E2852E`
- Label: "Apply" — sentence case, `#E2852E`, 16sp Regular
- Width: ~50% of screen, centred
- Height: 40dp
- Corner radius: 20dp
- **No fill colour** — gradient background shows through the button interior
- **No shadow**, no elevation
- Pressed state: background tints to `#E2852E` at 15% opacity, scale 0.97
- Disabled state (5s debounce): stroke opacity 0.4, label opacity 0.4
- Post-apply: button outline briefly flashes `#27AE60` for 300ms, then returns

## 5.3 Stats Row (three columns)

Directly below the Apply button. Three equally-spaced items, each consisting of:
1. Icon (28dp, `#E2852E`, outlined/line style Material symbol)
2. Value text (13sp, `#E2852E`) — directly below icon
3. Label text (11sp, `#E2852E`, lower opacity or same) — below value

| Column | Icon | Label |
| :---- | :---- | :---- |
| Left | `wb_sunny` (sun) | Exposure |
| Centre | `verified_user` or shield | Recommended SPF |
| Right | `hourglass_empty` | Last Applied |

- No background, no card, no separator — floats on gradient
- Equal column widths; content centre-aligned within each column

## 5.4 Application Log Section

- Section header: "Application Log" in cursive/script font, `#E2852E`, centred
- Log entries: plain timestamp list, `#E2852E`, 13sp Regular, centre-aligned
- **No row separators, no dividers, no alternating backgrounds**
- Timestamps stacked vertically with 8dp gap between entries
- Empty state: centred italic text "No applications logged yet today", `#E2852E` at 60% opacity
- New entry: fade-in from top, 200ms ease-out

## 5.5 Footer

- Text: *"Apply religiously, we don't want any sun damage this summer 🏖️"*
- Style: 11sp Italic, `#E2852E`, centre-aligned
- Position: pinned to bottom of scroll content (not fixed to viewport)

## 5.6 Tile Layout (Watch)

| Property | Value |
| :---- | :---- |
| Tile background | `#ABE0F0` (Sky Blue) gradient to `#F5C857` |
| Row 1 | UV Index (compact badge, 36dp) — left-aligned |
| Row 2 | SPF: [value] |
| Row 3 | Last applied: [time] / "Not yet today" |
| CTA button | "Apply Now" — outlined orange pill, bottom-aligned |
| Tile tap (non-button) | Opens main app |

## 5.7 Complication (Watch Face)

- Icon: `wb_sunny` vector — `#E2852E`, 24×24dp
- Short text: "UV [value]"
- Background tint: `#FFEE91` where slot permits
- Tap: deep-link to main app screen

## 5.8 Notifications

- Watch: icon ☀️, title bold, body max 2 lines, inline "Apply" action button (orange outlined)
- Phone: expandable; same content; tap opens companion app
- Notification channel: "Sun Protection Reminders"
- Importance: `IMPORTANCE_DEFAULT` — no interruption sound

---

# 6. Motion & Animation

Motion is warm and celebratory, never mechanical.

## 6.1 Apply Animation (Lottie)

| Property | Value |
| :---- | :---- |
| Duration | 1500ms |
| Character | Sun with sunglasses + thumbs up |
| Motion | Bounce in → hold → thumbs up → fade out |
| Colours | `#E2852E`, `#F5C857`, `#FFEE91` only — no blue or grey |
| Watch render | Full-screen overlay (fade in 100ms, fade out 200ms) |
| Phone render | Modal bottom sheet, 50% height, top corners rounded 24dp |
| Repeat | Play once per tap — never loops |

## 6.2 Screen Transitions (Watch)

| Transition | Spec |
| :---- | :---- |
| App open | Slide up from bottom, 280ms, ease-out-cubic |
| Scroll | Native Wear OS ScalingLazyColumn momentum — do not override |
| Log entry appears | Fade in + translate Y −8dp, 200ms |
| Button press | Scale 0.97 + stroke brightens, 100ms |

## 6.3 UV Badge Pulse (UV 8+)

- Outer ring: scale 1.0 → 1.12, opacity 0.4 → 0.0
- Duration: 1800ms per cycle, infinite loop
- Stops when UV drops below 8

---

# 7. Wear OS — Platform-Specific Rules

## 7.1 Screen Shape

- Galaxy Watch 8 screen is circular (396×396px)
- Apply `WindowInsets`-aware circular clip to root composable
- All primary interactive elements must be fully within the circle
- Use `ScalingLazyColumn` for the main scrollable content

## 7.2 Background (Watch)

- Background gradient: `#F5C857` top → `#ABE0F0` bottom
- **Do NOT use `#000000` OLED black on the watch** — the design uses the warm gradient on both platforms
- Text colour: `#E2852E` throughout

## 7.3 Interaction Model

- Crown (rotary input): must scroll `ScalingLazyColumn` — implement `onRotaryScrollEvent`
- Single tap: primary action
- Long press: not used in v1.0
- Swipe left/right: reserved for Wear OS system navigation — do not intercept

## 7.4 Performance on Watch

- No network calls on main thread
- Tile updates within 10 seconds (`WearTileService` contract)
- Lottie: pre-load on app start; do not lazy-load on tap
- Avoid custom canvas drawing in hot paths; use vector drawables

---

# 8. Android Phone — Platform-Specific Rules

## 8.1 Layout

- Single Activity, single Composable screen
- `LazyColumn` for full scrollable screen
- Status bar: transparent, icons tinted `#E2852E` (use `isAppearanceLightStatusBars = false` since gradient is warm/mid-tone)
- Navigation bar: edge-to-edge; add `WindowInsets.navigationBars` padding at bottom
- Gradient drawn with `Brush.verticalGradient(listOf(#FFEE91, #F5C857, #ABE0F0))` as `Modifier.background`

## 8.2 Visual Hierarchy on Phone

1. **Hero icon** (flower/sun) — largest element, top of screen
2. **Apply button** — primary action, prominent but compact
3. **Stats row** — supporting info
4. **Application Log** — reference data
5. **Footer** — motivational; decorative

## 8.3 What NOT to Do on Phone

- ❌ Do NOT use a white or `#FDFAF4` solid background
- ❌ Do NOT use cards or surfaces — no elevation, no shadow
- ❌ Do NOT use a full-width filled Apply button
- ❌ Do NOT use black or grey text anywhere
- ❌ Do NOT add navigation bars, tabs, or drawer menus
- ❌ Do NOT show a separate UV badge hero — the flower icon is the hero

---

# 9. Iconography & Imagery

## 9.1 Hero Icon (Phone)

- Shape: 6-petal flower / stylised sun, all petals rounded
- Colour: `#E2852E` filled, flat
- Size: 96–120dp, centred
- SVG path preferred; no raster assets

## 9.2 Launcher Icon

- Background: radial gradient `#F5C857` (centre) → `#E2852E` (edge)
- Foreground: simplified sun, 8 rays, 2dp stroke, `#FFFFFF`
- No text in icon
- Adaptive icon: foreground + background layers supplied separately

## 9.3 In-App Icons (Stats Row)

| Icon | Material Symbol | Style |
| :---- | :---- | :---- |
| Exposure (sun) | `wb_sunny` | Outlined, `#E2852E` |
| Recommended SPF (shield) | `verified_user` | Outlined, `#E2852E` |
| Last Applied (hourglass) | `hourglass_empty` | Outlined, `#E2852E` |

All icons: 28dp on phone, 24dp on watch.

## 9.4 Emoji Usage

- ☀️ in complication, notification title, log entries
- 🏖️ in footer text only
- No other emoji in UI

---

# 10. Design Do's and Don'ts

## 10.1 Do

- ✅ Use the full-screen warm-to-cool gradient as the background on both platforms
- ✅ Use `#E2852E` for every foreground element — icons, text, button strokes
- ✅ Use an outlined (stroke-only) Apply button — no fill
- ✅ Use the flower/sun as the hero icon at the top of the phone screen
- ✅ Use a cursive/script font for the "Application Log" section header
- ✅ Keep the log list as plain stacked timestamps — no separators, no rows
- ✅ Keep every screen to one primary action
- ✅ Ensure every interactive element meets 48×48dp minimum touch target
- ✅ Label UV level in text in addition to colour (accessibility)
- ✅ Animate the Apply action — celebration is part of the behaviour-building UX

## 10.2 Don't

- ❌ Don't use a solid background colour on phone (no white, no off-white, no `#FDFAF4`)
- ❌ Don't use `#000000` OLED black anywhere in the phone or watch UI
- ❌ Don't use grey, navy, purple, or green as UI colours
- ❌ Don't add cards, surfaces, shadows, or elevation to any element
- ❌ Don't use a filled Apply button — stroke/outline only
- ❌ Don't make the Apply button full-width — compact centred pill only
- ❌ Don't add row separators or dividers to the log list
- ❌ Don't add navigation bars, tabs, or drawer menus — one-screen app
- ❌ Don't use more than two font weights on any single screen
- ❌ Don't put any text on the Apply button except "Apply"
- ❌ Don't repeat UV index more than once per view

---

# 11. Pre-Delivery Design Checklist

Before any screen is marked complete, verify against this checklist:

| ☐ | Checklist Item |
| :---- | :---- |
| ☐ | Background is a full-screen gradient `#FFEE91` → `#F5C857` → `#ABE0F0` (top to bottom) on both platforms |
| ☐ | Every foreground element (text, icons, button stroke) is `#E2852E` |
| ☐ | No white, grey, black, or off-white surfaces appear anywhere in the design |
| ☐ | Hero icon is the flower/sun shape, centred, at top of phone screen |
| ☐ | Apply button is an outlined pill (stroke only, no fill), centred, ~50% screen width |
| ☐ | Stats row has exactly three columns: Exposure / Recommended SPF / Last Applied |
| ☐ | "Application Log" header uses a cursive/script font style |
| ☐ | Log entries are plain stacked timestamps — no row backgrounds, no separators |
| ☐ | Footer text matches exactly: "Apply religiously, we don't want any sun damage this summer 🏖️" |
| ☐ | Apply animation plays on every log action and does not loop |
| ☐ | Tile shows: UV index, required SPF, last application time, Apply Now button |
| ☐ | Complication shows sun icon + UV text; tap opens main app |
| ☐ | No cards, no shadows, no elevation on any element |
| ☐ | No more than two font weights on any single screen |
| ☐ | All icon sizes: 28dp (phone), 24dp (watch) |
| ☐ | Lottie animation colours: `#E2852E`, `#F5C857`, `#FFEE91` only |
| ☐ | All touch targets ≥ 48×48dp |

---

*End of Design Guidelines*

**SunGuard — "Warm. Clear. Protected."**
