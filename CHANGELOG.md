# 更新日志

## v4.21.15 (2026-08-20)

**外部 DLL 优先 + 双文件发布**

- exe 同目录存在 `Vape-v4.21Native.dll` 时，优先直接使用外部 DLL，不再解压内嵌副本（GUI 与 `-nogui` 两种模式均支持；便于单独替换/更新原生库，无需重发整个 exe）
- 外部 DLL 不存在时自动回退：解压内嵌 DLL 到 `<exe>\.vapeclient\Vape421Recovery\`（原有行为不变，仍不写 `%TEMP%`）
- 自动构建产物与 GitHub Release 现在同时附带 `Vape-v4.21.exe` 和 `Vape-v4.21Native.dll`（双文件发布）

## v4.21.14 (2026-08-20)

**生命值 HUD 修复**

- 修复生命值在所有现代版本（1.21.11 / 26.1.2 等）完全不显示：根因是 `ActiveModuleStackFrame` 用 Minecraft 原生 `FontRenderer.drawStringWithShadow`，而 1.20.6+ 该旧签名不存在（基于 GuiGraphics）→ 静默不绘制。改为统一使用 Vape 自绘字体（SmoothFontRenderer），与 Keystrokes 等正常 HUD 帧一致
- 生命值居中坐标对齐其他 HUD 帧的实际坐标系（`窗口/(2×缩放)`），字号调大至 1.5 倍更清晰

**寻找方块（Search）修复**

- 修复现代版本搜不到方块：`ClientChunkProvider.chunkListing` 字段在现代 `ClientChunkCache` 不存在，改为按玩家周围区块坐标扫描；`Block.t` 现代版增加物品注册表 fallback
- 现代版扫描改由 SearchProcessor 单路径负责（避免双扫描并发清空结果）
- 翻译：寻找矿物 → **寻找方块**（Search 为通用方块搜索器，可搜任意方块）

**翻译与界面**

- 输入框不再自动翻译用户输入（输入 Diamond 不再变钻石，仅 placeholder 翻译）
- 天神搭路 → 神桥（godbridge）

**渲染修复**

- 补交 3D 渲染修复（26.1.2/1.21.10 事件钩子矩阵、实例化渲染器矩阵、视口/FBO 状态），修正矿物边框错位

## v4.21.13 (2026-08-20)

**1.7.10 兼容性修复（SilentAura / XRay）**

- SilentAura（1.7.10）：攻击直接锁定目标实体（`PlayerControllerMP.attackEntity`），不再依赖准星 rayTrace——"右键打准星所指而非目标"修复
- SilentAura（1.7.10）：移除发包视角改写——此前改写服务器视角导致服务器频繁发 S08 位置/视角同步包，把本地视角拉向目标、移动被拉回，并触发实体 tick NPE 崩溃
- SilentAura（1.7.10）：攻击时显式 `swingItem()` 播放本地挥动动画并广播挥动包（其他玩家可见），本地视角完全不转动（全静默）
- SilentAura（1.7.10）：隐藏无用的"瞄准速度"设置（该版本不发包旋转，设置不生效；1.12.2+ 仍可用）
- XRay（1.7.10）：修复开启闪退（Tessellator isDrawing 字段缺失保护）、非目标方块完全隐藏（稳定跨 chunk 重建）、矿洞模式（六方向相邻判断暴露矿块）、矿洞模式切换即刷新、隐藏无效透明度滑块
- 翻译：比较器→计数器、后视镜 Level view→平视、生物名（牛/猪/狗/熊/虎等）字库全覆盖、TargetInfo tooltip 补译、按键绑定/徽章文案修复

## v4.21.12 (2026-08-19)

**通知文案翻译修复**

- 修复自救/搭梯失败通知（"Server rejected block placement!" 等）仍显示英文：根因是通知文本经 WrappedTextComponent 按空格拆行后再做整句翻译匹配，拆行后的碎片永远匹配不到完整句子键
- `WrappedTextComponent.getWrappedLines()` 改为在拆行前先对完整文本翻译一次，未知文本（玩家名/动态消息）原样返回不受影响
- 顺带补译 server_rejected_block_placement 键（服务器拒绝了方块放置！），并核查全部救援失败通知（Clutch Failed / AutoLadder Failed / Server teleported you! / No ladder available! / No support block available! / Could not find a valid laddering solution!）均已有翻译

## v4.21.11 (2026-08-19)

**翻译与界面完善**

- 修复按键绑定文案仍显示英文：`Properties` 键不允许空格，改用下划线键（`UI_PRESS_A_KEY_TO_BIND` 等），JVM 级验证全部按键提示（按任意键绑定 / 绑定已移除 / 已绑定 / 必须先绑定 / 通过按键使用 / 设置按键 / 输入物品名称 / 正在编辑收藏）映射正确
- 补译界面徽章与占位文案：New!→新！、UNSAFE→不安全、INDEV→开发中、Beta/BETA→测试版、Type message...→输入消息...、User is offline→用户已离线、Click to remove bind→点击移除绑定（补全全大写 BETA 键）
- 术语统一：锚点→重生锚、救场→自救、上帝视角→自由视角（Freecam）、围堵→围墙（BlockIn）、变速齿轮→变速（Timer）、距离→攻击距离（Reach）、自由视角→自由旋转视角（Freelook）
- 部分翻译中文化：Combo→连击、Kite→风筝、overlay→覆盖层、nametags→名称标签、Bot→机器人、Post→后置；CPS / GUI / FPS / ESP / WTap / 甩枪 保留原文（社区通用）
- 修复中文字体过细：noto.ttf 重建时固定字重为 SemiBold(600)（NotoSansSC-VF 默认 Thin(100) 导致文字发虚），新增 `tools/rebuild_noto.py` 一键重建脚本
- 修复退出世界闪退：`CoordinatesHudFrame.getBiomeName` 空世界保护（退出世界后 HUD 坐标框多渲染一帧导致 NPE）

## v4.21.10 (2026-08-18)

**文件落盘收拢（不再写 %TEMP%）**

- 所有运行产物全部收进 exe 旁隐藏的 `.vapeclient` 目录，不再向 `%TEMP%` 写入任何文件：
  - 内嵌 DLL / 产品 JAR 解压到 `<exe>\.vapeclient\Vape421Recovery\`（原 `%TEMP%\Vape421Recovery\`）
  - 注入目录不再经 `%TEMP%\injector_dir.txt` 传递：DLL 从自身模块路径（固定位于 `.vapeclient\Vape421Recovery`）推断 exe 目录，彻底废弃跨进程 TEMP 标记
  - 注入诊断改为写到 DLL 模块目录（`.vapeclient\Vape421Recovery\vape_injector_diag.txt`），不再写 `%TEMP%\vape_injector_diag.txt`
  - Java 侧映射失败转储改到 `.vapeclient\Vape421Recovery\`（原 `java.io.tmpdir`）
  - 在线纹理缓存从 `~/vapeTextures` 移入 `.vapeclient\vapeTextures`
  - 服务数据 `vape-service.json` 与本地配置统一到 exe 旁 `.vapeclient`（跟随 DLL 注入的 `vape.directory`）
- `.vapeclient` 目录自动设为隐藏属性（GUI / 命令行 / DLL 三处创建时均设置），Explorer 中不再显眼
- 启动时顺手清理旧版本遗留的 `%TEMP%\injector_dir.txt` / `vape_injector_diag.txt`

**修复**

- 修复 `.vapeclient` 落点与 exe 目录不一致：GUI 注入不再依赖可能过期的 TEMP 标记，路径始终由注入链路内的模块位置推导

## v4.21.9 (2026-08-17)

**GUI 单文件加载器**

- 上游 VapeLoader（GDI+ 图形界面）集成进主产物：登录 / 浏览器授权 / 进程选择 / 注入进度界面，全中文化
- 去掉登录页：启动即进入 Minecraft 进程选择，本地生成 token，无需外部服务
- 去掉缓存询问页：注入完成直接显示加载完成页
- 窗口标题「Vape v4」；图标与产品一致（vape-v4.21.ico 嵌入）
- 移除缓存询问与外部 DLL 加载：GUI 与命令行模式均从内嵌资源（ID 422 RCDATA）解压注入，不加载外部 DLL
- 支持命令行模式：`Vape-v4.21.exe -nogui [pid]` 启动命令行注入器（进程选择器 / 指定 PID）
- 错误页去掉「联系支持」按钮，「复制错误」居中

**产物精简**

- 移除 Vape421Injector / Vape421InjectorStandalone 目标与单独 DLL 产物
- 最终构建产物仅保留一个单文件：`Vape-v4.21.exe`（内嵌 DLL + 图标 + 全部资源）

**修复**

- 修复 AutoTotem 创造模式无法装备副手图腾：根因是物品栏打开判断未识别创造物品栏（GuiContainerCreative），导致每 tick 重复开背包、点击永不执行；现识别创造物品栏并改用 PICKUP 拿起→放下（ClickType.SWAP 在创造模式被新版拒绝）
- 修复进程选择器长标题显示不全：加宽标题绘制区域，超长标题用 GDI+ 实测宽度二分截断并追加省略号（中英文混排精确）
- 修复注入报「产品 DLL 拒绝套接字引导块」：消除登录死锁（token 本地生成）
- 兼容性审计：AutoTotem 等 ≥1.21.4 模块在老版本自动跳过，不会误加载或崩溃

## v4.21.8 (2026-08-17)

**上游新功能集成**

- 合并上游 VapeV4.21 新模块：AutoMace（自动重锤，含重锤选择 / 眩晕猛击 / 瞄准范围 / 自动卸下鞘翅 / 仅猛击 / 显示快捷栏）、NoItemRelease（不释放物品）、PearlCatch（接住珍珠）
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

**本地化与界面（正式版补充）**

- 服务启动可配置：`VAPE_BIND_ADDRESS` / `VAPE_HTTP_PORT` / `VAPE_ZEUS_PORT` / `VAPE_DATA_FILE` 环境变量（默认 127.0.0.1 / 8080 / 8091，绑定 0.0.0.0 可局域网访问）
- 中文字体加粗：noto.ttf 由 Thin(100) 字重改为 SemiBold(600) 字重子集（322KB），界面文字更清晰
- 补全翻译：PearlCatch（瞄准模式/蓄力延迟/向上/当前瞄准）、AutoMace（密度/破甲模式名、概率、目标设置）、InventoryOverlay（物品栏覆盖标题与提示）、KillAura 完美挥击提示、「完美挥击」tooltip 等
- 修正翻译：「修改方块人（我的世界）游戏时间。」→「修改游戏时间。」
- 模块搜索同时匹配英文名与中文翻译名（中文可直接搜到模块）
- 分类导航放开「其他」分类入口：新版模块页与旧版 GUI 均显示，可直接查看 Other 分类模块（如不释放物品）

**标准译名与字库同步**

- mace 改用官方译名「重锤」（原误译「狼牙棒」），涉及 AutoMace 模块名、设置项、物品名与全部 tooltip 共 17 处
- 修正翻译：投掷器颜色（原错位为「投掷器潜影贝」）、smash（重击）与 breach（破甲）混淆、删除重复的 smash_only 死值
- 字库按更新后的翻译字符集重新子集化，补入「锤」等新字形（stb 验证 1248 字符 0 缺失）

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
