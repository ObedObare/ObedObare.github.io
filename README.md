# Lumina Studios — Android App

A native Kotlin Android app for **Lumina Studios** (photography & cinematography).
Includes a sign-up / sign-in gate, gallery of sample photos, and a video reel.

## Open in Android Studio

1. Unzip this archive somewhere on your machine.
2. Open **Android Studio → File → Open…** and select the unzipped
   `LuminaStudios/` folder (the one containing `settings.gradle.kts`).
3. Wait for Gradle sync to finish. Android Studio will download the Gradle
   wrapper (8.7) and dependencies automatically. **Internet required** on
   first sync.
4. Pick an emulator or connected device (min SDK 24 / Android 7.0).
5. Press **Run ▶** to build & launch.

## Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17 (bundled with recent Android Studio versions)
- Android SDK Platform 34

## Features

- Sign up / Sign in (stored locally via SharedPreferences — demo only)
- Auth gate: gallery is only reachable after login
- Photo grid with category tags
- Photo detail screen
- Built-in sample video reel (`res/raw/sample.mp4`)
- Custom Lumina Studios launcher icon
- Dark theme with gold accents (matches the brand)

## Project structure

```
LuminaStudios/
  settings.gradle.kts
  build.gradle.kts
  gradle/wrapper/gradle-wrapper.properties
  app/
    build.gradle.kts
    src/main/
      AndroidManifest.xml
      java/com/lumina/studios/
        LoginActivity.kt
        GalleryActivity.kt
        PhotoDetailActivity.kt
        VideoActivity.kt
        PhotoAdapter.kt
        SessionManager.kt
        Photo.kt
      res/
        layout/  values/  drawable/  raw/sample.mp4  mipmap-*/
```

## Notes

- The local auth is intentionally simple for demo purposes. For production,
  swap `SessionManager` for Firebase Auth / your backend.
- Sample images & video are bundled inside the APK — no network needed
  to view the gallery.
