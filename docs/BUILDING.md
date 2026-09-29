# Building

## Requirements

- JDK 21 for the 1.21.1 projects, JDK 25 for the 26.1.2 projects. Gradle toolchains download the
  right JDK automatically through the Foojay resolver if it is missing.
- An internet connection for the first build (Minecraft and loader artifacts are downloaded).

## Build one target

```bash
cd forge-26.1.2
./gradlew build
```

Output: `build/libs/areyousure-<loader>-<minecraft>-<version>.jar`.

## Run the game in development

```bash
./gradlew runClient
```

## Toolchain per folder

| Folder             | Gradle plugin                         | Gradle | Java |
|--------------------|---------------------------------------|--------|------|
| `neoforge-1.21.1`  | ModDevGradle 2.0.x                    | 9.x    | 21   |
| `neoforge-26.1.2`  | ModDevGradle 2.0.x                    | 9.x    | 25   |
| `forge-1.21.1`     | ForgeGradle 7                         | 9.3    | 21   |
| `forge-26.1.2`     | ForgeGradle 7                         | 9.5    | 25   |

## Known noise

ForgeGradle 7 prints a `Mavenizer.assertNotCacheOnly` stack trace during some builds. It is a
cache check logged at debug level and does not fail the build.

---

# Compilation

- JDK 21 pour les projets 1.21.1, JDK 25 pour les projets 26.1.2 (téléchargé automatiquement par
  Gradle si absent).
- Entrez dans le dossier voulu puis lancez `./gradlew build`. Le jar se trouve dans `build/libs/`.
- `./gradlew runClient` lance le jeu en mode développement.
- La trace `Mavenizer.assertNotCacheOnly` affichée par ForgeGradle 7 est sans conséquence.
