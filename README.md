[简体中文](README.md) | [English](README_EN.md)

# Vape 4.21 Product Recovery

Vape 4.21 Java 层与 Windows x64 原生桥接层的研究性恢复工程。

> GitHub 仓库：[RSSeeker/Vape-v4.21](https://github.com/RSSeeker/Vape-v4.21) ·
> 发布页：[Releases](https://github.com/RSSeeker/Vape-v4.21/releases)
>
> 单文件注入器产物名为 `Vape-v4.21.exe`，由 GitHub Actions 自动构建并在打标签时发布。

> 源代码来源：[OpenVapeCN/OpenVape](https://github.com/OpenVapeCN/OpenVape)
> （本项目基于该公开仓库的源代码进行恢复、整理与本地化）。

## 与上游源代码的改动

相对 [OpenVapeCN/OpenVape](https://github.com/OpenVapeCN/OpenVape) 源码的主要改动：

**功能**

- 新增 26.1.x / 26.2.x 运行时版本探测（按 `version.json` 家族匹配，协议 100/110），26.1.2 实测通过
- 配置本地持久化：写入 `.vapeclient\config.json`（位于 EXE/DLL 同目录）——模块设置、配置、好友、框架位置本地保存，自动保存 + 退出兜底，加载本地优先；`autoSave` 默认开启
- 原生日志与 Java 日志统一到 `.vapeclient\log\`，每次注入生成新的日志文件
- 单文件注入器：`Vape-v4.21.exe` 内嵌完整 DLL 与 Java 载荷，无需附带文件即可使用
- 注入器控制台中文化并启用 UTF-8 输出（中文窗口标题正常显示）

**汉化**

- 语言包由 798 键扩充至 2600+ 键（选项、提示、教程、确认框、药水/物品名等全覆盖）
- 默认语言改为中文
- 修复多行提示换行被压平、颜色码 § 丢失导致的翻译不匹配
- HUD 模块栈（26.x 多模块及旧版 MC 字体路径）补上翻译

**字体与显示**

- `noto.ttf` 替换为覆盖全部翻译字符的 Noto Sans SC 子集（700 粗体，0 缺字）
- 自定义圆角图标嵌入 `Vape-v4.21.exe`

**构建与工程**

- CMake 自动探测 Visual Studio 生成器（vswhere），兼容 VS2022 / VS2026
- MSVC `/utf-8` 编译选项；Release 产物不再附带 README
- 新增 GitHub Actions 发布工作流（打 `v*` 标签自动构建发布，含 `-pre` 的标签为预发布）

### 它不是 Vape 官方源码、原始发布包或厂商签名产物，也不保证具备与原产品完全一致的行为。

> 本项目用于软件恢复、兼容性分析和自有环境测试。仅应在你拥有并获准测试的隔离实例中
> 使用，并自行确认当地法律、软件许可和服务器规则。

## Minecraft 兼容性

| Minecraft | Vanilla | Forge | Fabric |
| --- | :---: | :---: | :---: |
| 1.7.10 | ✓ | ✓ | - |
| 1.8.9 | ✓ | ✓ | - |
| 1.12.2 | ✓ | ✓ | - |
| 1.16.5 | | | |
| 1.21.11 | ✓ | ✓ | ✓ |
| 26.2 | ✓ | ✓ | ✓ |

也支持 Lunar Client 与 Badlion Client 1.8.9 实例注入。

Minecraft 1.16.5 的支持不佳，部分映射、渲染和模块功能可能无法正常工作。

**对于26.2版本，请在进入服务器或单人世界后注入**。

所有目标实例均须使用 64 位 JVM。

## 环境要求

仅编译和校验 Java 层需要：

- JDK 17，用作 Gradle toolchain；输出默认通过 `--release 8` 编译
- 项目自带的 Gradle Wrapper；构建脚本固定要求 Gradle 8.8
- 可访问 Maven Central 和 Gradle Plugin Portal 的网络连接

构建 native bundle 还需要：

- Windows x64
- Visual Studio 2022 C++ x64 工具链及 Windows SDK
- CMake 3.21 或更高版本
- 一套包含 JNI/JVMTI 头文件的 JDK；面向 1.7.10、1.8.9 和 1.12.2 测试时建议使用 JDK 8

## 快速开始

在 PowerShell 中进入仓库根目录：

```powershell
.\gradlew.bat clean build verifyInjectionPayload
```

该命令会完成以下工作：

1. 编译恢复源码并处理全部资源。
2. 检查源码数量以及残留的致命 CFR 反编译标记。
3. 生成包含运行时依赖的 injection JAR。
4. 确认载荷包含必要包，且所有 class 均可由 Java 8 加载。

主要 Java 产物位于 `build/libs/`。如需生成 IntelliJ IDEA 工程配置，可运行：

```powershell
.\gradlew.bat idea
```

## 构建原生测试包

```powershell
.\gradlew.bat prepareInjectionBundle -PtargetRelease=8 `
  -PnativeJavaHome="C:\Program Files\Java\jdk1.8.0_301"
```

完整测试包输出到 `build/injection/`：

```text
Vape421Native.dll
Vape421Injector.exe
README.md
```

DLL 将 Java injection JAR 作为 `RCDATA` 嵌入，不要求另行放置 payload。原生桥接层只实现
从样本九项 `RegisterNatives` 表恢复出的接口；未在样本中注册的额外 Java native 声明
不会被臆造实现。更多细节见 [`native/README.md`](native/README.md)。

## 隔离环境运行

启动使用 64 位 JVM 的受支持 Minecraft 实例（包括 1.21.11/26.2 Fabric）或 Lunar Client 实例后，在
`build/injection/` 中执行：

```powershell
.\Vape421Injector.exe <pid> .\Vape421Native.dll
```

注入器仅执行 `LoadLibraryW`。DLL 加载后会等待 JVM 与 Minecraft `Client thread`，通过其
上下文 ClassLoader 加载内嵌 JAR；Fabric 实例会通过 Fabric Launcher API 将载荷加入 Knot
ClassLoader。随后 DLL 注册九个 native 方法，并调用
`gg.vape.runtime.NativeBridge.start()`。执行结果写入 DLL 同目录的
`vape421-native.log`。

## 常用校验任务

| 命令 | 用途 |
| --- | --- |
| `.\gradlew.bat check` | 编译、源码覆盖与恢复质量检查 |
| `.\gradlew.bat injectionJar` | 构建自包含 Java 注入载荷 |
| `.\gradlew.bat verifyInjectionPayload` | 检查依赖完整性与 Java 8 字节码版本 |
| `.\gradlew.bat buildNative` | 构建 x64 DLL 和注入器 |
| `.\gradlew.bat prepareInjectionBundle` | 汇总可供隔离测试的 native bundle |

## 许可证

本仓库以 [CC0 1.0 Universal](LICENSE) 方式提供。在适用范围内，CC0 仅覆盖仓库贡献者
有权作出处分的内容；第三方库、商标、字体、纹理以及其他既有材料仍受其各自权利约束。
