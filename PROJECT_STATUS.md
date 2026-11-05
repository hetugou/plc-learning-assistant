# PROJECT_STATUS — 项目状态

> 本文件记录项目当前真实进展。每次任务结束后更新，不得写入未完成的事项为「已完成」。

最后更新：2026-11-05

---

## 1. 项目目标

制作 **Android PLC 学习助手 APK**：以离线方式教授 PLC 电气控制基础（DI/DO/AI/AO、常开常闭、电气控制练习），
并逐步扩展到梯形图仿真、工厂实战案例与多品牌指令学习。

---

## 2. 技术架构

- 应用外壳：**Kotlin**（Android 原生）
- 界面承载：**Android WebView**
- 前端：**HTML / CSS / JavaScript**，全部内置于 `app/src/main/assets/`
- 运行模式：**完全离线**，无 CDN、无远程 API、无网络图片
- 权限：**不申请 `INTERNET`**
- 目标兼容：**Android 15（API 35）优先**

详细约束见 `AGENTS.md`。

---

## 3. 当前已完成内容

- [x] GitHub 仓库建立并关联（SSH Deploy Key，读写权限已验证）
- [x] 开发规范文件建立：`AGENTS.md`、`PROJECT_STATUS.md`、`CHANGELOG.md`
- [x] 开发阶段计划（V1.0 → V1.3）确定并记录
- [x] Android 工程骨架：Gradle 8.9 Wrapper、AGP 8.7.3、Kotlin 2.0.21、compileSdk/targetSdk 35、minSdk 26
- [x] `MainActivity` + WebView：加载内置 assets，仅放行 `file:///android_asset/`，禁止网络与外部跳转
- [x] 内置前端骨架：`index.html` / `css/style.css` / `js/app.js`，无任何外部资源
- [x] GitHub Actions 构建工作流 `.github/workflows/android.yml`
- [x] 静态检查通过：Manifest 无 `INTERNET` 权限、assets 无外链、HTML/JS/CSS/XML 语法合法、Gradle 配置与依赖解析成功

**应用当前只是可启动的空壳**：首页仅有学习路线占位，没有任何 PLC 教学内容。

**构建产物尚未验证**：开发机为 ARM64，Google 的 `aapt2` 仅有 x86-64 版本，本地无法打包；
APK 必须由 GitHub Actions（x86-64）产出，其首次运行结果尚未确认。

---

## 4. 当前未完成内容

- [ ] CI 首次构建结果确认（APK 是否实际产出）
- [ ] APK 真机安装与离线运行验证
- [ ] 全部 V1.0 教学内容（首页导航可交互、DI/DO、AI/AO、NO/NC、电气控制练习，见第 5 节）
- [ ] 本地学习进度保存
- [ ] 应用图标与 release 签名配置
- [ ] 单元测试
- [ ] `README.md`

---

## 5. 下一阶段开发任务

### V1.0：基础 APK

1. Android 项目框架（Kotlin + Gradle + WebView，`targetSdk 35`，无 `INTERNET` 权限）
2. 首页与学习导航
3. DI、DO、AI、AO 基础教学
4. NO / NC（常开 / 常闭）教学
5. 电气控制基础交互练习
6. 本地学习进度保存
7. 完全离线运行验证

### V1.1：梯形图仿真

常开/常闭触点、线圈、串联、并联、自锁、互锁、TON 定时器、CTU 计数器、单步运行与状态显示。

### V1.2：工厂实战

气动阀控制与反馈、4M310 电磁阀、行程开关与 DI 信号、4–20mA 模拟量、电机启停与软启动器、工厂故障排查案例。

### V1.3：品牌学习

西门子、三菱、汇川 PLC；明确区分不同品牌的指令集与地址格式。

---

## 6. 已知问题与技术风险

| 风险 | 说明 | 应对 |
|---|---|---|
| 本地不可打包 | 开发机为 ARM64，Google `aapt2` 仅有 x86-64，本地 `assembleDebug` 必然失败（`error=2`） | APK 只走 GitHub Actions（x86-64）；本地仅做静态检查与 Gradle 配置验证 |
| CI 结果待确认 | 私有仓库，本地无 API 凭据，无法读取 Actions 运行状态 | 在仓库 Actions 页面确认，或提供只读 PAT |
| WebView 版本差异 | 不同设备 WebView 内核版本不同，CSS/JS 特性支持不一致 | 避免使用过新特性，必要时降级实现 |
| 资源体积 | 离线内置图片/字体可能增大 APK | 优先矢量图（SVG/VectorDrawable）与系统字体 |
| 网络被误引入 | 后续易顺手加 CDN 或在线字体 | 代码审查时检查外链，`INTERNET` 权限保持未申请 |
| 本地存储方案 | 进度保存需选择 SharedPreferences / Room / localStorage，尚未决定 | V1.0 设计时确定并在文档记录 |
| 梯形图仿真性能 | 扫描周期与前端渲染可能冲突 | V1.1 采用固定步进 + 状态快照设计 |

---

## 7. 当前 Git 与版本状态

- 分支：`main`
- 远端：`origin` → `ssh://git@ssh.github.com:443/hetugou/plc-learning-assistant.git`
- 当前版本：`0.2.0`（与 `versionName` 一致，工程骨架）
- 最近提交：`2a290d3 build: 建立 Android 工程骨架与 GitHub Actions 构建`
- 构建状态：**云端待验证**（本地 ARM64 不可打包，CI 为唯一出口）
- 测试状态：**无测试代码**，仅完成静态检查与 Gradle 配置/依赖解析验证

---

## 8. 下一步建议

1. 先闭合构建闭环：确认 GitHub Actions 首次运行结果，下载 `app-debug-apk` 并装机验证离线启动。
2. 构建确认后进入内容开发：首页导航 → DI/DO 教学 → NO/NC → 交互练习 → 进度保存。
3. 补充发布要素：应用图标、`README.md`、基础单元测试。
