[English](README_EN.md) | [简体中文](README.md)

# Vape 4.21 Product Recovery

A research-oriented recovery project for the Vape 4.21 Java layer and Windows x64 native bridge layer.

> GitHub repository: [RSSeeker/Vape-v4.21](https://github.com/RSSeeker/Vape-v4.21) ·
> Releases: [Releases](https://github.com/RSSeeker/Vape-v4.21/releases)
>
> Main artifacts: `Vape-v4.21.exe` (single-file injector, embeds the DLL),
> `Vape-v4.21Injector.exe` (injector without the embedded DLL), and
> `Vape-v4.21Native.dll`.

> Source code origin: [OpenVapeCN/OpenVape](https://github.com/OpenVapeCN/OpenVape)
> (This project recovers, reorganizes and localizes the source code from that public repository).

## Changes relative to the upstream source

Main changes compared with [OpenVapeCN/OpenVape](https://github.com/OpenVapeCN/OpenVape):

**Features**

- Added runtime detection for the 26.1.x / 26.2.x family (matched by `version.json`, protocol 100/110); verified on 26.1.2
- Local configuration persistence: written to `.vapeclient\config.json` next to the EXE/DLL — module settings, profiles, friends and frame positions are saved locally, auto-saved plus shutdown fallback, and local data is loaded first; `autoSave` is enabled by default
- Native and Java logs are unified under `.vapeclient\log\`, with a new log file per injection
- Single-file injector: `Vape-v4.21.exe` embeds the complete DLL and Java payload, no extra files required
- Chinese-localized injector console with UTF-8 output

**Localization**

- Language pack expanded from 798 keys to 2600+ keys (options, tooltips, tutorials, dialogs, potion/item names, etc.)
- Chinese is the default language
- Fixed multi-line tooltip newline flattening and lost § color codes that broke exact matching
- HUD module stack (26.x multi-module and legacy MC-font paths) now translates
- Fixed runtime-composed strings in dropdowns and the target filter (whole-string lookup first, per-part translation fallback; e.g. `Mode - Simple`, `Target: Players`, `Ignore none`)
- Fixed trailing-space corruption on the `ignore`/`ignoring`/`mobs`/`peaceful` key values that broke lookup, and added word keys such as `target`, `invisible`, `naked` and lowercase `none`

**Fonts & visuals**

- `noto.ttf` replaced with a Noto Sans SC subset covering every translated character (700 bold, zero missing glyphs)
- Custom rounded-corner icon embedded into `Vape-v4.21.exe`

**Build & tooling**

- CMake auto-detects the Visual Studio generator via vswhere (VS2022 / VS2026)
- MSVC `/utf-8` compile option; README is no longer attached to release assets
- GitHub Actions (ci / release) compile the Java layer with JDK 17 (`--release 8` produces Java 8 bytecode) and install JDK 8 to provide JNI/JVMTI headers for building the x64 native bridge layer

### It is NOT official Vape source code, an original release package, or a vendor-signed artifact, and it does not guarantee behavior identical to the original product.

> This project is intended for software recovery, compatibility analysis, and testing in self-owned environments. It should only be used in isolated instances that you own and are authorized to test, and you are responsible for verifying local laws, software licenses, and server rules.

## Minecraft Compatibility

| Minecraft | Vanilla | Forge | Fabric |
| --- | :---: | :---: | :---: |
| 1.7.10 | ✓ | ✓ | - |
| 1.8.9 | ✓ | ✓ | - |
| 1.12.2 | ✓ | ✓ | - |
| 1.16.5 | | | |
| 1.21.11 | ✓ | ✓ | ✓ |
| 26.2 | ✓ | ✓ | ✓ |

Injection into Lunar Client and Badlion Client 1.8.9 instances is also supported.

Support for Minecraft 1.16.5 is poor; certain mappings, rendering, and module features may not function properly.

**For version 26.2, please inject after joining a server or singleplayer world.**

All target instances must use a 64-bit JVM.

## Requirements

Required only for compiling and verifying the Java layer:

- JDK 17, used as the Gradle toolchain; target output is compiled via `--release 8` by default
- Project-bundled Gradle Wrapper; build script strictly requires Gradle 8.8
- Internet connection with access to Maven Central and Gradle Plugin Portal

Required for building the native bundle:

- Windows x64
- Visual Studio 2022 C++ x64 toolchain and Windows SDK
- CMake 3.21 or higher
- A JDK containing JNI/JVMTI headers; JDK 8 is recommended when testing against 1.7.10, 1.8.9, and 1.12.2

## Quick Start

In PowerShell, navigate to the repository root directory:

```powershell
.\gradlew.bat clean build verifyInjectionPayload
```

This command performs the following tasks:

1. Compiles the recovered source code and processes all resources.
2. Checks source count and remaining fatal CFR decompilation markers.
3. Generates the injection JAR containing runtime dependencies.
4. Confirms that the payload includes necessary packages and that all classes can be loaded by Java 8.

Main Java artifacts are located in `build/libs/`. To generate IntelliJ IDEA project configurations, run:

```powershell
.\gradlew.bat idea
```

## Building Native Test Bundle

```powershell
.\gradlew.bat prepareInjectionBundle -PtargetRelease=8 `
  -PnativeJavaHome="C:\Program Files\Java\jdk1.8.0_301"
```

The complete test bundle outputs to `build/injection/`:

```text
Vape-v4.21.exe           single-file injector (embeds the DLL)
Vape-v4.21Injector.exe   injector (does not embed the DLL)
Vape-v4.21Native.dll
README.md
```

The DLL embeds the Java injection JAR as an `RCDATA` resource, so placing a payload separately is not required. The native bridge layer only implements interfaces recovered from the sample's nine-method `RegisterNatives` table; additional Java native declarations not registered in the sample will not be fabricated. For more details, see [`native/README.md`](native/README.md).

## Running in an Isolated Environment

After launching a supported Minecraft instance (including 1.21.11/26.2 Fabric) or Lunar Client instance using a 64-bit JVM, execute the following in `build/injection/`:

```powershell
.\Vape-v4.21Injector.exe <pid> .\Vape-v4.21Native.dll
```

The injector only performs `LoadLibraryW`. Once loaded, the DLL waits for the JVM and Minecraft `Client thread`, and loads the embedded JAR via its context ClassLoader. Fabric instances add the payload to the Knot ClassLoader via the Fabric Launcher API. Subsequently, the DLL registers nine native methods and calls `gg.vape.runtime.NativeBridge.start()`. A new per-injection `vape421-native-<pid>-<timestamp>.log` is written under `.vapeclient\log\`.

## Common Verification Tasks

| Command | Purpose |
| --- | --- |
| `.\gradlew.bat check` | Compile, source coverage, and recovery quality verification |
| `.\gradlew.bat injectionJar` | Build self-contained Java injection payload |
| `.\gradlew.bat verifyInjectionPayload` | Verify dependency integrity and Java 8 bytecode version |
| `.\gradlew.bat buildNative` | Build x64 DLL and injector |
| `.\gradlew.bat prepareInjectionBundle` | Assemble native bundle ready for isolated testing |

## License

This repository is provided under [CC0 1.0 Universal](LICENSE). To the extent applicable, CC0 covers only content that repository contributors have the right to dispose of; third-party libraries, trademarks, fonts, textures, and other existing materials remain subject to their respective rights.
