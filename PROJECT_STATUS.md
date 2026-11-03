# PROJECT_STATUS — 项目状态

> 本文件记录项目当前真实进展。每次任务结束后更新，不得写入未完成的事项为「已完成」。

最后更新：2026-11-03

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

**当前仓库中尚无任何 Android 工程代码，无应用源码、无构建脚本。**

---

## 4. 当前未完成内容

- [ ] Android 工程骨架（Gradle 配置、`AndroidManifest.xml`、`MainActivity`、WebView 承载）
- [ ] 前端资源目录与基础页面框架
- [ ] 全部 V1.0 功能（见第 5 节）
- [ ] 构建与真机验证流程
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
| 构建环境未验证 | 当前工作环境为 Ubuntu 容器，尚未确认 Android SDK / Gradle 可用性与版本 | V1.0 开始时先验证工具链，再写代码 |
| WebView 版本差异 | 不同设备 WebView 内核版本不同，CSS/JS 特性支持不一致 | 避免使用过新特性，必要时降级实现 |
| 资源体积 | 离线内置图片/字体可能增大 APK | 优先矢量图（SVG/VectorDrawable）与系统字体 |
| 网络被误引入 | 后续易顺手加 CDN 或在线字体 | 代码审查时检查外链，`INTERNET` 权限保持未申请 |
| 本地存储方案 | 进度保存需选择 SharedPreferences / Room / localStorage，尚未决定 | V1.0 设计时确定并在文档记录 |
| 梯形图仿真性能 | 扫描周期与前端渲染可能冲突 | V1.1 采用固定步进 + 状态快照设计 |

---

## 7. 当前 Git 与版本状态

- 分支：`main`
- 远端：`origin` → `ssh://git@ssh.github.com:443/hetugou/plc-learning-assistant.git`
- 当前版本：`0.1.0`（文档基线，无应用代码）
- 基线提交：`chore: initialize repository`（空树）+ 本次文档提交
- 构建状态：**不可构建**（尚无 Android 工程）
- 测试状态：**无测试**（尚无代码）

---

## 8. 下一步建议

从 **Android 工程骨架** 开始：先建立可编译的最小 Kotlin + WebView 工程并确认本地构建通过，
再依次实现首页导航 → DI/DO 教学 → NO/NC → 练习 → 进度保存。
