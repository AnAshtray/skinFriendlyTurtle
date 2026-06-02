# Sungaurd — sunguard-design

Standalone Cursor workspace for the **Sungaurd** product: UI design (Google Stitch) and Android implementation in this repo.

## Open in Cursor

1. **File → Open Folder**
2. Choose: `/home/ayeshaandroid/Desktop/Sungaurd/sunguard-design`

Work only in this folder—do not assume or reference other codebases unless the user explicitly points to them.

## Project layout

```
sunguard-design/
├── docs/PRD.md              # Synced from Stitch (SunGuard_PRD_v1.0.md)
├── docs/design-assets/      # Design system markdown from Stitch
├── app/                     # Android phone companion (Compose)
├── wear/                    # Wear OS app (foundation scaffold)
├── core/domain/             # Shared domain layer (pure Kotlin)
├── stitch/                  # Stitch exports (HTML, PNG)
└── .cursor/                 # MCP + rules
```

## Stitch MCP

Configured in `.cursor/mcp.json` via `scripts/stitch-mcp-proxy.mjs` (avoids Cursor “0 tools” issue).

### After opening this folder

1. Fully **quit and restart Cursor**
2. **Settings → Tools & MCP** → confirm **stitch** shows a non-zero tool count
3. Enable Stitch tools in Agent when designing

### Secrets

- API key and project ID: `.env` (gitignored)
- Template: `.env.example`
- Rotate keys at [stitch.withgoogle.com](https://stitch.withgoogle.com) if exposed

### Pinned Stitch project

See [STITCH_PROJECT.md](./STITCH_PROJECT.md) — project ID `17562754834645466144`.

### Example Agent prompts

- “List all screens in the pinned Stitch project”
- “Export screen HTML to `stitch/`”
- “Generate a mobile onboarding screen matching our design system”

## Android development

- **Phone:** `./gradlew :app:assembleDebug` → `app/build/outputs/apk/debug/app-debug.apk`
- **Wear:** `./gradlew :wear:assembleDebug`
- Copy `local.properties.example` → `local.properties` and set `sdk.dir`
- Architecture: Clean layers per `.cursor/rules/android-architecture.mdc` (`data` / `domain` / `ui`)
- **Sprint 1 (current):** phone main screen, DataStore log, Open-Meteo UV integration, midnight WorkManager reset, wear scaffold
- **Next:** Lottie apply animation, reminder engine, tile/complication, Wear Data Layer sync
