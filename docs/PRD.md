☀️

**SunGuard**

Product Requirements Document

Samsung Galaxy Watch 8 \+ Android Companion App

*Version 1.0  |  May 2026*

# **1\. Executive Summary**

SunGuard is a minimalist sun-protection assistant delivered as a Samsung Galaxy Watch 8 (Wear OS) native app with an Android companion app. Its singular mission is to make sunscreen reapplication effortless, contextually intelligent, and habit-forming without demanding attention from the user.

The app monitors ambient light/UV conditions, tracks user application history, and delivers smart reminders only when contextually relevant — eliminating alert fatigue. Every design and engineering decision defers to the core mission: protect the user’s skin, unobtrusively.

| One-Line Product Vision "The watch on your wrist that remembers to protect your skin when you forget." |
| :---- |

# **2\. Product Overview**

## **2.1 Platform Targets**

| Primary | Samsung Galaxy Watch 8 — Wear OS 4.x (Kotlin / Jetpack Compose for Wear OS) |
| :---- | :---- |

| Companion | Android 12+ smartphone (Kotlin / Jetpack Compose) |
| :---- | :---- |

| Min SDK | Wear OS: API 30  |  Phone: API 31 |
| :---- | :---- |

| Connectivity | Watch ↔ Phone via Wearable Data Layer API (WearableListenerService) |
| :---- | :---- |

## **2.2 Core Value Propositions**

* Context-aware reminders: alerts only when UV/ambient light thresholds make protection necessary

* Zero-friction logging: a single tap records application with timestamp

* Daily history reset: slate clears at midnight so users see only today’s data

* Unified experience: watch and phone share the same data, look, and feel

* Delight moment: celebratory sun-with-sunglasses animation on every application log

## **2.3 Out of Scope (v1.0)**

* iOS / Apple Watch support

* Cloud sync or multi-device history beyond same-day

* Sunscreen product database or recommendations

* Skin-type personalisation

* Social or gamification features

# **3\. Target Users & Personas**

## **3.1 Primary Persona — “The Outdoor Active”**

| Name | Aisha, 28 |
| :---- | :---- |

| Device | Samsung Galaxy Watch 8 \+ Android phone |
| :---- | :---- |

| Context | Runs outdoors, cycles, works near large office windows |
| :---- | :---- |

| Pain Point | Forgets reapplication after sweating or 2+ hours in sun |
| :---- | :---- |

| Goal | Simple tap-to-log without opening an app each time |
| :---- | :---- |

## **3.2 Secondary Persona — “The Skin-Conscious Professional”**

| Name | Daniel, 35 |
| :---- | :---- |

| Device | Samsung Galaxy Watch 8 \+ Android phone |
| :---- | :---- |

| Context | Works near floor-to-ceiling windows; commutes by car |
| :---- | :---- |

| Pain Point | Unaware of UV exposure through glass; no reminders during day |
| :---- | :---- |

| Goal | Ambient UV awareness without checking phone |
| :---- | :---- |

# **4\. Functional Requirements**

## **4.1 Watch App — Main Screen (Single Scrollable View)**

The watch app is a single vertically-scrollable Wear OS screen. Sections are revealed by crown-scroll or swipe, following the order below.

### **Section 1 — Apply Button (Top, Always Visible)**

* Large, full-width circular primary button labelled “Apply ☀️”

* On tap: record UTC timestamp of application to local DataStore

* Trigger sun-with-sunglasses Lottie animation (1.5 s, non-blocking)

* Haptic confirmation: single 150 ms pulse (WearableHapticFeedback.VIRTUAL\_KEY\_HOLD)

* Debounce: ignore duplicate taps within 5 seconds

### **Section 2 — UV & SPF Dashboard (Scroll Down)**

* Current UV Index: large numeric badge with colour coding (see Section 9\)

* Recommended SPF: derived value — SPF \= 2.5 × UVI, rounded up to nearest SPF tier (15/30/50/50+)

* Data source: OpenUV API (primary) with fallback to Open-Meteo UV endpoint

* Refresh: every 15 minutes while screen is active; on-demand by crown-tap

* Last updated timestamp shown below badge

### **Section 3 — Application Log (Scroll Down)**

* Chronological list of today’s application times

* Format: “09:42 AM — Applied ☀️”

* Max 20 entries visible (scrollable within section)

* Empty state: “No applications logged yet today”

* Resets to empty at 00:00 device local time

### **Section 4 — Motivational Footer (Bottom)**

* Static text: “Apply religiously, we don’t want any sun damage this summer Ἵ6️”

* Styled in soft yellow; non-interactive

## **4.2 Watch App — Tile**

A Wear OS Tile (androidx.wear.tiles) provides glanceable at-a-wrist-raise information.

* Row 1: Current UV Index with colour pill

* Row 2: Required SPF value

* Row 3: Last application time (e.g. “Last applied: 11:04 AM”) or “Not yet today”

* Quick-action button: “Apply Now” — triggers the same log-and-animate flow as main screen without opening the app

* Tile refresh interval: every 15 minutes or on data change via TileService.getUpdater()

* Tap anywhere else on tile: launches full watch app

## **4.3 Watch App — Complication**

A ComplicationDataSourceService for watch-face customisation slots.

* Complication type: SHORT\_TEXT

* Icon: ☀️ (sun emoji as vector drawable)

* Short text: current UV index (e.g. “UV 7”)

* Tap action: deep-link PendingIntent launches full watch app main screen

* Supported slot types: SMALL\_IMAGE, SHORT\_TEXT, LONG\_TEXT

* Update cadence: 15-minute minimum (ComplicationDataSourceService.MIN\_PERIOD\_MINUTES \= 15\)

## **4.4 Launcher Icon**

* Standard Wear OS round launcher icon: sun graphic on gradient background (\#E2852E → \#F5C857)

* Launches MainActivity which renders the main scrollable screen

* Icon size: 108×108 px (xxxhdpi) per Wear OS guidelines

## **4.5 Smart Notification / Reminder Engine**

The reminder engine is the core intelligence of SunGuard. It runs as a Wear OS ForegroundService and evaluates the following rules on a 5-minute background polling loop.

### **Rule 1 — Outdoor High UV (Highest Priority)**

| Trigger Conditions Ambient light sensor reading ≥ 10,000 lux AND Current UV Index ≥ 3 AND User has been in this state for ≥ 120 continuous minutes AND No application logged in the past 120 minutes |
| :---- |

* Action: Fire notification on both watch AND phone

* Watch notification: “Time to reapply\! ☀️ You’ve been in the sun for 2+ hours.” \+ Apply inline action button

* Phone notification: same text \+ tap-to-log deep link

* Repeat: every 30 minutes until user applies or moves indoors

### **Rule 2 — High Ambient Light / Near Window (Medium Priority)**

| Trigger Conditions Ambient light sensor reading ≥ 3,000 lux AND \< 10,000 lux AND UV Index ≥ 3 AND User has been in this state for ≥ 120 continuous minutes AND No application logged in the past 120 minutes |
| :---- |

* Action: Fire notification on both watch AND phone

* Watch notification: “Sunscreen reminder ☀️ You’re near bright light. Reapply to stay protected.”

* Phone notification: same text \+ tap-to-log deep link

* Repeat: every 60 minutes until user applies or light drops

### **Rule 3 — Suppress (No Alert)**

| Suppress When Any Of: Ambient light sensor \< 3,000 lux, OR UV Index \< 3, OR User applied sunscreen \< 120 minutes ago |
| :---- |

### **Sensor Data Strategy**

* Primary: device ambient light sensor (Sensor.TYPE\_LIGHT) — always available

* Secondary: UV index from OpenUV API (requires GPS permission for location)

* Fallback: if network unavailable, use cached UV data ≤ 1 hour old; if stale, use lux-only heuristic

* Battery optimisation: batch sensor reads every 5 minutes via SensorManager.registerListener with SENSOR\_DELAY\_NORMAL

## **4.6 Application Log — Data Lifecycle**

* Storage: Jetpack DataStore (Preferences) on watch; synced to phone via Data Layer API

* Retention: same calendar day (00:00–23:59 local time)

* Midnight reset: WorkManager periodic task (15-min flex window) clears the log

* Max entries per day: 50 (thereafter oldest entry removed — FIFO)

* Sync: phone reads watch DataStore via ChannelClient on open

## **4.7 Phone Companion App — Main Screen**

The Android companion app mirrors the watch experience on a larger canvas. It is a single-Activity, single-Screen Compose UI.

* Section 1 (top): Large “Apply” button with sun-with-sunglasses Lottie animation on tap

* Section 2: UV Index card \+ Required SPF card (side by side on phone width)

* Section 3: Scrollable daily application log list

* Section 4 (bottom): Motivational footer text

* Midnight reset identical to watch logic

* Notifications: receives watch-originated notifications AND generates its own per reminder engine (running as a JobScheduler job on phone)

# **5\. Non-Functional Requirements**

## **5.1 Performance**

| App cold start | \< 2 seconds on Galaxy Watch 8 |
| :---- | :---- |

| Tile render | \< 500 ms from TileRequest |
| :---- | :---- |

| UV data refresh | \< 3 seconds on LTE/WiFi |
| :---- | :---- |

| Animation | 60 fps, no jank (use Lottie hardware-accelerated renderer) |
| :---- | :---- |

## **5.2 Battery**

* Background service must not consume more than 3% battery per hour

* All sensor polling via batched SensorManager; no continuous wake lock

* Use Doze-safe WorkManager for midnight reset job

## **5.3 Permissions**

* android.permission.ACCESS\_FINE\_LOCATION — UV API geo-lookup

* android.permission.BODY\_SENSORS — ambient light (SENSOR\_TYPE\_LIGHT is non-body, no special permission needed on Wear OS, but declare for OEM compatibility)

* android.permission.FOREGROUND\_SERVICE — reminder engine

* android.permission.POST\_NOTIFICATIONS — Android 13+ notification permission

* android.permission.VIBRATE — haptic feedback

## **5.4 Privacy & Data**

* No data transmitted off-device except UV API call (location only, no PII)

* UV API key stored in BuildConfig/Secrets Gradle Plugin — never in source

* Application log data never leaves device

* Location used for UV lookup only; not stored or logged

## **5.5 Accessibility**

* All interactive elements minimum 48×48 dp touch target

* Content descriptions on all icons and image buttons

* Colour is never the sole differentiator — UV level also shown as text

* Support for Wear OS font scaling

# **6\. API Integration**

## **6.1 OpenUV API**

| Endpoint | GET https://api.openuv.io/api/v1/uv |
| :---- | :---- |

| Params | lat, lng (from FusedLocationProviderClient) |
| :---- | :---- |

| Auth | x-access-token header (API key from BuildConfig) |
| :---- | :---- |

| Response fields used | result.uv (current), result.uv\_max (daily peak) |
| :---- | :---- |

| Quota | Free tier: 50 req/day — cache aggressively |
| :---- | :---- |

| Fallback | Open-Meteo: GET https://api.open-meteo.com/v1/forecast?hourly=uv\_index |
| :---- | :---- |

## **6.2 SPF Calculation Logic**

| SPF Derivation Formula Raw SPF \= UV Index × 2.5 SPF 15 → if UV Index ≤ 5 SPF 30 → if UV Index 6–7 SPF 50 → if UV Index 8–10 SPF 50+ → if UV Index \> 10 |
| :---- |

# **7\. Key User Flows**

## **7.1 Happy Path — Outdoor Reminder & Application**

1. User goes outside; ambient lux rises above 10,000

2. Watch background service detects 120 min outdoor threshold breach

3. Notification appears on watch face: “Time to reapply\!” with “Apply” inline action

4. User taps “Apply” inline action on notification

5. Sun-with-sunglasses Lottie animation plays on watch

6. Timestamp logged; notification dismissed

7. Timer resets — next reminder in 120 minutes

## **7.2 Quick-Log via Tile**

8. User raises wrist; Tile is visible

9. Tile shows: UV 8 | SPF 50 | Last applied 10:30 AM

10. User taps “Apply Now” on tile

11. Animation \+ haptic; log updated; tile refreshes instantly

## **7.3 Complication Tap Flow**

12. User glances at watch face; sees ☀️ UV 6 complication

13. User taps complication

14. Watch app main screen opens at Section 2 (UV dashboard)

## **7.4 Daily Reset**

15. At 00:00 device local time, WorkManager job fires

16. DataStore log cleared on watch and phone

17. Tile refreshes to show “Not yet today”

# **8\. Animation Specification**

## **8.1 Apply Animation**

| Trigger | Every time user logs an application (button, tile, notification action) |
| :---- | :---- |

| Asset type | Lottie JSON animation |
| :---- | :---- |

| Content | Sun character with sunglasses, thumbs up, brief bounce \+ glow |
| :---- | :---- |

| Duration | 1.5 seconds, auto-dismiss |
| :---- | :---- |

| Watch | Full-screen overlay, non-interactive during playback |
| :---- | :---- |

| Phone | Modal bottom-sheet overlay, 50% screen height |
| :---- | :---- |

| Repeat | Never loops; plays once per apply tap |
| :---- | :---- |

| Lottie lib | com.airbnb.android:lottie:6.x (phone), com.airbnb.android:lottie-compose:6.x (both) |
| :---- | :---- |

# **9\. UV Colour Coding**

| UV Index Range | Label | Hex | Use |
| :---- | :---- | :---- | :---- |
| 0 – 2 | Low | \#FFEE91 | Light yellow fill |
| 3 – 5 | Moderate | \#F5C857 | Medium yellow fill |
| 6 – 7 | High | \#E2852E | Orange fill |
| 8 – 10 | Very High | \#E2852E \+ bold | Orange fill \+ bold text |
| 11+ | Extreme | \#C0392B | Red fill \+ bold |

# **10\. Technical Architecture**

## **10.1 Watch-Side Modules**

* MainActivity (Compose for Wear OS) — single-activity app

* TileService extends WearTileService — renders Tile

* ComplicationDataSourceService — provides complication data

* ReminderForegroundService — sensor polling \+ notification dispatch

* UVRepository — fetches & caches UV index (OkHttp \+ Gson)

* ApplicationLogRepository — DataStore read/write \+ Data Layer sync

* MidnightResetWorker (WorkManager) — daily log clear

## **10.2 Phone-Side Modules**

* MainActivity (Jetpack Compose) — companion UI

* WearDataListenerService — receives sync updates from watch

* ReminderJobService (JobScheduler) — parallel reminder logic for phone notifications

* ApplicationLogRepository — mirrors watch repository

* MidnightResetWorker — same logic as watch

## **10.3 Shared Data Contract (Wearable Data Layer)**

| Path: /sun\_log | JSON array of ISO-8601 timestamps (today’s applications) |
| :---- | :---- |

| Path: /uv\_data | JSON: { uv\_index: float, spf: int, updated\_at: long } |
| :---- | :---- |

| Message: /apply\_now | Trigger from phone to watch to log application (and vice versa) |
| :---- | :---- |

# **11\. Acceptance Criteria**

## **11.1 Reminder Engine**

18. Given user is outdoors (lux ≥ 10k) for 121 minutes with no prior log, THEN notification fires on watch AND phone within 1 minute of threshold breach

19. Given user applies sunscreen (taps Apply), THEN no reminder fires for the next 119 minutes

20. Given ambient lux drops below 3,000, THEN all pending reminders are cancelled

21. Given UV index \< 3, THEN no reminders fire regardless of lux

## **11.2 Log & Reset**

22. Given user taps Apply at 14:33, THEN log shows “02:33 PM — Applied ☀️”

23. Given midnight passes, THEN log is empty at 00:01

24. Given 50 log entries exist and user taps Apply, THEN oldest entry is removed (FIFO)

## **11.3 UV Data**

25. Given network available, THEN UV index updates within 3 seconds of screen open

26. Given network unavailable, THEN cached data ≤ 1 hour old is displayed with “cached” indicator

27. Given UV \= 8, THEN required SPF shows as 50

## **11.4 Animation**

28. Given Apply button tapped, THEN Lottie animation plays within 200 ms of tap

29. Given animation playing, THEN second tap within 5 seconds is ignored

# **12\. Delivery Milestones**

| Sprint | Focus | Deliverables |
| :---- | :---- | :---- |
| S1 | Foundation | Project setup, Wear OS scaffold, DataStore, basic UV API integration, launcher icon |
| S2 | Watch Core UI | Main screen (all 4 sections), Apply button, log display, Lottie animation |
| S3 | Tile & Complication | Tile with quick-apply action, complication with deep-link |
| S4 | Reminder Engine | Sensor polling, reminder rules (R1 \+ R2 \+ R3), watch \+ phone notification dispatch |
| S5 | Phone Companion | Android companion app, Data Layer sync, phone notifications, midnight reset |
| S6 | Polish & QA | Design review against Design.md, edge cases, battery profiling, accessibility audit |

# **13\. Open Questions & Decisions**

* Q1: Should the midnight reset be configurable (e.g., user sets their “skin day” start time)?  → Deferred to v1.1

* Q2: OpenUV free quota is 50 req/day — is a paid key required for production?  → Budget decision needed

* Q3: Should watch-originated Apply taps sync in real-time to phone app?  → Recommendation: yes, via Data Layer MessageClient (\< 1 s latency)

* Q4: Complication slot SMALL\_IMAGE vs SHORT\_TEXT — user testing needed to confirm preferred format

* Q5: Should notification repeat interval for Rule 1 (30 min) be user-configurable?  → Deferred to v1.1

*End of Product Requirements Document*

**SunGuard v1.0 — “Protect your skin. One tap at a time.”**