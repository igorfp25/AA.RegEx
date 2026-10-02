# AA RegEx

AA RegEx is a custom Automation Anywhere package that adds regular-expression actions for bots:

- **RegEx partial match** checks whether any part of an input string matches a regex pattern.
- **Extract named RegEx group** finds all matches and returns the values captured by a specified named group as a list.

Both actions support choosing whether matching is case-sensitive.

## Requirements

- Java 11
- The package SDK libraries required by the Gradle build in the `libs` directory

## Build

From the repository root, run:

```text
./gradlew.bat clean build shadowJar
```

In PowerShell, the equivalent command is:

```powershell
.\gradlew.bat clean build shadowJar
```

The Gradle wrapper downloads and uses the configured Gradle version. The build and Shadow JAR outputs are written under `build/`.
