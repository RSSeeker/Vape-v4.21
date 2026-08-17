# 更新日志

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
