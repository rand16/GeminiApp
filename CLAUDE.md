# CLAUDE.md - GeminiApp Project Guidelines

## Build & Development
- Build Project: `./gradlew assembleDebug`
- Run Tests: `./gradlew test` (Unit), `./gradlew connectedAndroidTest` (Instrumented)
- Clean Build: `./gradlew clean`
- Lint Check: `./gradlew lint`

## Coding Guidelines
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Dependency Management**: Gradle Version Catalog (`libs.versions.toml`)
- **Naming**: Follow standard Kotlin/Android naming conventions.
- **Style**: Prefer concise, functional-style Kotlin where appropriate.

## Project Structure
- `/app`: Main application module.
- `/gradle`: Gradle wrapper and version catalog.
