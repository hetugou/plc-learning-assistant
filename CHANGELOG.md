# CHANGELOG

本项目所有版本变化记录于此，格式参考 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.1.0/)，
版本号遵循语义化版本（`MAJOR.MINOR.PATCH`）。

记录规则：
- 每个版本注明版本号与日期（`YYYY-MM-DD`）。
- 变更按类型分类：`新增` / `变更` / `修复` / `移除` / `安全`。
- 不记录未实际完成的内容；未发布的内容标为 `未发布`。

---

## [未发布]

（暂无）

---

## [0.2.0] - 2026-11-03

### 新增
- Android 工程骨架：Gradle 8.9 Wrapper、AGP 8.7.3、Kotlin 2.0.21，`compileSdk`/`targetSdk` 35、`minSdk` 26。
- `MainActivity`：WebView 加载内置 `assets/index.html`；仅放行 `file:///android_asset/`，拦截一切外部跳转；
  关闭文件/content 访问、混合内容与网络加载，禁止脚本弹窗与新窗口。
- 内置离线前端骨架：`index.html`、`css/style.css`、`js/app.js`，无 CDN、无远程图片。
- GitHub Actions 工作流 `.github/workflows/android.yml`：JDK 17 + Android SDK 35 + build-tools 35.0.0，
  执行 `assembleDebug` 并上传 `app-debug-apk`，失败时保留构建日志。
- `gradle.properties`、`.gitignore`。

### 说明
- 应用不声明 `INTERNET` 权限。
- 本版本仅为可启动的空壳，**未包含任何 PLC 教学内容**。
- APK 构建结果**尚未验证**：开发机为 ARM64，`aapt2` 仅提供 x86-64，本地无法打包，需由 CI 产出。

---

## [0.1.0] - 2026-11-03

### 新增
- 建立项目开发规范 `AGENTS.md`：技术架构、离线约束、代码规范、测试要求、修改范围纪律。
- 建立项目状态文档 `PROJECT_STATUS.md`：目标、架构、已完成/未完成项、阶段任务、风险清单、Git 状态。
- 建立本变更日志 `CHANGELOG.md`。
- 确定开发阶段计划：V1.0 基础 APK、V1.1 梯形图仿真、V1.2 工厂实战、V1.3 品牌学习。

### 说明
- 本版本仅包含文档，无 Android 工程代码，仓库当前不可构建。
