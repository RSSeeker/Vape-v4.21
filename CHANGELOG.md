# 更新日志

## v4.21.8 (2026-08-17)

**上游新功能集成**

- 合并上游 VapeV4.21 新模块：AutoMace（自动狼牙棒，含狼牙棒选择 / 眩晕猛击 / 瞄准范围 / 自动卸下鞘翅 / 仅猛击 / 显示快捷栏）、NoItemRelease（不释放物品）、PearlCatch（接住珍珠）
- 合并 InventoryOverlay（物品栏覆盖显示）组件与其设置页，可在 HUD 设置页开启
- 合并 Badlion 旧版按键事件队列（BadlionKeyBindingEventQueue / Badlion189InputQueueMappingTask），Badlion 客户端按键兼容
- 移除与现有 Animations 模块重复的上游 BlockHit 模式文件（保留原 Animations 的 Manual / Predict / Auto / Lag 模式）

**内嵌 VapeService（加载器配套服务）**

- 将上游 VapeService（HTTP 8080 + Zeus TCP 8091）整体集成进单文件注入包，游戏内自动后台启动，无需单独运行服务 jar
- VapeService 全部 14 个 Java 文件降级为 Java 8 语法（record / `Set.copyOf` / `.toList` / `String.isBlank` / `Optional.stream` / `Optional.isEmpty` / `HexFormat` / `Files.readString` / netty 4.1 API 等），通过 `verifyInjectionPayload`（major ≤ 52）检查
- 服务数据存于 `~/.vapeclient/vape-service.json`；端口被占用时自动向上探测空闲端口，启动失败静默降级不影响游戏

**字库修复（重要）**

- 修复中文界面缺字：原 noto.ttf 为子集字体，缺少 释 / 猛 / 卸 / 鞘 / 翅 / 观 / 晋 / 房 / 址 / 订 / 资 / 料 / 钥 共 13 个字形，其中「不释放物品」「自动卸下鞘翅」「重新装备鞘翅」等新翻译会显示空白/方框
- 使用系统 NotoSansSC 重新子集化生成静态 TTF（去除可变字体表），覆盖全部 1250 个翻译字符，经 stb（游戏实际渲染引擎）验证 0 缺失；旧字体备份于 `noto.old.ttf.backup`

## v4.21.7 (2026-08-17)

**本地化与界面修复**

- 修复中文界面下模块设置值名（如完美挥击、需要鼠标按下等）显示英文：语言初始化提前到启动阶段，不再受 GUI 渲染时序影响
- 修复切换到 English 语言后模块子选项文字不渲染：值行换行缓存随语言切换失效重建
- 语言选项精简为「中文 / English」两种
- 大量补全翻译：SilentAura/各类模块值名与提示、AntiDebuff、MLG 水桶、BlockIn 黑名单、CrystalAura 效率/防自杀、完整性检查、NBT 标签、使用好友、重置角度、在线状态页（Error establishing / Registration offline / 重连倒计时）等
- 换行提示文本整串翻译（`WrappingTextLabelComponent` 先翻译再换行）
- 修复 Frame 设置导航标题（「Profiles settings / Friends settings」→「配置 设置 / 好友 设置」）与 Overlay 标题翻译
- 列表 / 白名单条目不再经过 GUI 翻译表，显示配置原文（默认例子显示英文 Zombie / Skeleton / Creeper / Spider，输入什么显示什么）
- 注入完成通知、在线重连文案中文化

**按键显示（Keystrokes）**

- WASD / LMB / RMB 等按键文字与图标在按键格内居中显示

## v4.21.6 (2026-08-15)

**渲染修复**

- 修复 26.1.2 查找矿物（Search）3D 方块轮廓不渲染：`EventRender3D` 钩子改用 `modelViewMatrix` 参数，实例化渲染器矩阵正确；修复 `RenderBatchState` 字段初始化顺序、1×1 视口与 FBO 绑定问题
- 修复 26.1.2 相机矩阵来源（改用 `GameRenderer` 主相机四元数），Search 方块轮廓不再错位/漂移

**刷怪笼查找（SpawnerFinder）**

- 修复中文客户端（26.1.2）白名单无法匹配：白名单条目同时支持本地化名称（僵尸）、资源键（`minecraft:zombie`）与英文名（Zombie）匹配
- 渲染标签本地化：「僵尸 刷怪笼」（实体名 + 距离）
- 移除调试诊断日志

**本地化与界面**

- HUD 设置翻译「抬头显示」→「显示」
- 列表 / 白名单条目不再经过 GUI 翻译表，一律显示配置原文（默认例子显示英文 Zombie / Skeleton / Creeper / Spider，输入什么显示什么）
- 内置配置文件档按钮恢复英文：Classic PVP / Modern PVP

## v4.21.5

- 时钟（Clock）HUD：位置夹取屏幕内、模块列表打开时隐藏
- 后视镜（Rearview）：离屏渲染 HUD 重叠抑制、UV 镜像修复；26.1.2 上隐藏（<1.16.5 约束）
- 配置目录定位：`.vapeclient` 优先位于注入器 EXE 同目录
- 注入器控制台中文横幅

## v4.21.4 及更早

- 配置本地持久化（`.vapeclient\config.json`）
- 完整中文本地化（2600+ 键）、默认中文
- 26.1.x / 26.2.x 运行时版本探测
- 单文件注入器、统一日志目录
