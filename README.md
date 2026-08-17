[简体中文](README.md) | [English](README_EN.md)

# Vape 4.21 Product Recovery

Vape 4.21 Java 层与 Windows x64 原生桥接层的研究性恢复工程。

> GitHub 仓库：[RSSeeker/Vape-v4.21](https://github.com/RSSeeker/Vape-v4.21) ·
> 发布页：[Releases](https://github.com/RSSeeker/Vape-v4.21/releases)
>
> 主要产物：`Vape-v4.21.exe`（单文件注入器，内嵌 DLL）、
> `Vape-v4.21Injector.exe`（不内嵌 DLL 的注入器）、`Vape-v4.21Native.dll`。

> 源代码来源：[OpenVapeCN/OpenVape](https://github.com/OpenVapeCN/OpenVape)
> （本项目基于该公开仓库的源代码进行恢复、整理与本地化）。

## 与上游源代码的改动

相对 [OpenVapeCN/OpenVape](https://github.com/OpenVapeCN/OpenVape) 源码的主要改动：

**功能**

- 新增 26.1.x / 26.2.x 运行时版本探测（按 `version.json` 家族匹配，协议 100/110），26.1.2 实测通过
- 配置本地持久化：写入 `.vapeclient\config.json`（优先位于注入器 EXE 同目录，其次 DLL 同目录，最后 `%APPDATA%`）——模块设置、配置、好友、框架位置本地保存，自动保存 + 退出兜底，加载本地优先；`autoSave` 默认开启
- 原生日志与 Java 日志统一到 `.vapeclient\log\`，每次注入生成新的日志文件
- 单文件注入器：`Vape-v4.21.exe` 内嵌完整 DLL 与 Java 载荷，无需附带文件即可使用
- 注入器控制台中文化并启用 UTF-8 输出（中文窗口标题正常显示）

**汉化**

- 语言包由 798 键扩充至 2600+ 键（选项、提示、教程、确认框、药水/物品名等全覆盖）
- 默认语言改为中文
- 修复多行提示换行被压平、颜色码 § 丢失导致的翻译不匹配
- HUD 模块栈（26.x 多模块及旧版 MC 字体路径）补上翻译
- 修复下拉框与目标过滤器等运行时拼串的翻译（先查整串、未命中再逐段翻译；如 `Mode - Simple`、`Target: Players`、`Ignore none`）
- 修复语言包中 `ignore`/`ignoring`/`mobs`/`peaceful` 键值尾随空格导致的查表失效，并补齐 `target`/`invisible`/`naked`/小写 `none` 等单词键

**字体与显示**

- `noto.ttf` 替换为覆盖全部翻译字符的 Noto Sans SC 子集（700 粗体，0 缺字）
- 自定义圆角图标嵌入 `Vape-v4.21.exe`

**构建与工程**

- CMake 自动探测 Visual Studio 生成器（vswhere），兼容 VS2022 / VS2026
- MSVC `/utf-8` 编译选项；Release 产物不再附带 README
- GitHub Actions（ci / release）使用 JDK 17 编译 Java 层（`--release 8` 输出 Java 8 字节码），并安装 JDK 8 提供 JNI/JVMTI 头文件构建 x64 原生桥接层

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
| 26.1.2 | ✓ | ✓ | ✓ |
| 26.2 | ✓ | ✓ | ✓ |

也支持 Lunar Client 与 Badlion Client 1.8.9 实例注入。

Minecraft 1.16.5 的支持不佳，部分映射、渲染和模块功能可能无法正常工作。

**对于 26.1.2 与 26.2 版本，请在进入服务器或单人世界后注入**。

所有目标实例均须使用 64 位 JVM。

## 环境要求

仅编译和校验 Java 层需要：

- JDK 17，用作 Gradle toolchain；输出默认编译为 Java 17 字节码，传
  `-PtargetRelease=8` 可输出 Java 8 字节码（CI 构建即采用该参数）
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
Vape-v4.21.exe           单文件注入器（内嵌 DLL）
Vape-v4.21Injector.exe   注入器（不内嵌 DLL）
Vape-v4.21Native.dll
README.md
```

DLL 将 Java injection JAR 作为 `RCDATA` 嵌入，不要求另行放置 payload。原生桥接层恢复
样本的 `RegisterNatives` 接口表，另将样本未实现声明的 native 方法注册为安全占位桩，
避免 `UnsatisfiedLinkError`。更多细节见 [`native/README.md`](native/README.md)。

## 隔离环境运行

推荐直接运行单文件注入器 `Vape-v4.21.exe`（内嵌完整 DLL 与 Java 载荷）：启动使用 64 位
JVM 的受支持 Minecraft 实例（包括 1.21.11、26.1.2、26.2 Fabric）或 Lunar Client 实例后，
在 `build/injection/` 中直接运行，会出现自动刷新的 Java 游戏窗口选择器（↑/↓ 选择，
回车注入，Esc 退出）。

也可用命令行方式指定进程注入：

```powershell
.\Vape-v4.21Injector.exe <pid> .\Vape-v4.21Native.dll
```

注入器仅执行 `LoadLibraryW`。DLL 加载后会等待 JVM 与 Minecraft `Client thread`，通过其
上下文 ClassLoader 加载内嵌 JAR；Fabric 实例会通过 Fabric Launcher API 将载荷加入 Knot
ClassLoader。随后 DLL 注册原生方法，并调用
`gg.vape.runtime.NativeBridge.start()`。每次注入的日志位于注入器 EXE 同目录的
`.vapeclient\log\vape421-native-<pid>-<时间戳>.log`。

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

## 版本更新日志

详见 [CHANGELOG.md](CHANGELOG.md)。

### v4.21.8 (2026-08-17)

- 合并上游新模块：AutoMace（自动狼牙棒）、NoItemRelease（不释放物品）、PearlCatch（接住珍珠）、InventoryOverlay（物品栏覆盖显示）
- 内嵌 VapeService（HTTP + Zeus 服务）进单文件注入包，游戏内自动后台启动，数据存于 `~/.vapeclient/`
- 修复中文界面缺字：noto.ttf 重新子集化，覆盖全部翻译字符（stb 验证 0 缺失），「自动卸下鞘翅」「不释放物品」等不再显示方框
- VapeService 全部代码降级为 Java 8 字节码，通过 `verifyInjectionPayload` 检查

### v4.21.7 (2026-08-17)

- 修复中文界面模块设置值名显示英文（语言初始化时序）与 English 语言下子选项文字不渲染
- 语言选项精简为「中文 / English」
- 大量补全翻译（模块值名/提示、在线状态页、设置导航标题等）
- 按键显示（Keystrokes）WASD 等文字居中
- 列表 / 白名单条目显示配置原文

### v4.21.6 (2026-08-15)

- 修复 26.1.2 查找矿物（Search）3D 方块轮廓不渲染与相机矩阵问题
- 修复刷怪笼查找（SpawnerFinder）中文客户端白名单无法匹配（支持本地化名 / 资源键 / 英文名）
- 刷怪笼渲染标签本地化：「僵尸 刷怪笼」
- HUD 设置翻译「抬头显示」→「显示」
- 列表 / 白名单条目不再经过 GUI 翻译表，显示配置原文；配置文件档按钮恢复英文（Classic PVP / Modern PVP）

