# Calendar Sync (minimal ICSx⁵ fork)

A stripped-down fork of [ICSx⁵](https://github.com/bitfireAT/icsx5) by bitfire web engineering. It subscribes to an iCalendar (`.ics`) link and syncs it into Android's calendar storage. Any app that reads the device calendar can then show those events, including the Minimal launcher.

The main use is a **Proton Calendar share link**, since Proton doesn't sync to the device calendar itself.

## Changes from upstream

- New app id `dev.minimal.icsync`, name "Calendar Sync", and sync-account type, so it installs alongside the official ICSx⁵ without clashing.
- Removed: the donation button and dialog, the winter easter egg, translations, the Play/standard build flavours, fastlane metadata and upstream CI.
- Release builds are signed with the local debug key, for sideloading.
- The sync engine (fetching, parsing, writing to the calendar provider, scheduled sync) is unchanged from upstream.

## Build

```sh
scripts/build-cert4android.sh     # once: JitPack can't build the pinned cert4android commit
./gradlew assembleRelease         # app/build/outputs/apk/release/calendar-sync-*.apk
```

Requires JDK 21 and the Android SDK.

## Licence

GPL-3.0, same as upstream (see `LICENSE`). Copyright © bitfire web engineering and contributors; modifications © 2026. The in-app About screen still shows the licence and upstream attribution.
