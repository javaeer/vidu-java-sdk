# 更新日志 / Changelog

本文件记录项目所有 notable 变更，格式遵循 [Keep a Changelog](https://keepachangelog.com/)，版本号遵循 [语义化版本](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### 计划 / Planned
- 补充 HTTP 超时 / 重试 / 限流封装
- 修复 `ViduResponse` 字段 `@JsonProperty` 映射隐患
- 启用被跳过的单元测试并补充 CI

## [0.0.1-SNAPSHOT] - 开发中

### 新增 / Added
- `ViduClient`：文生视频、图生视频、参考生视频、首末帧生视频、模板生视频、任务查询
- `ViduClientConfig` / `ViduClientConfigUtils` 内存配置注入
- 参数枚举与前置校验（`ModelEnums` / `DurationEnums` / `ResolutionEnums` / `AspectRatioEnums` / `StyleEnums` / `TemplateEnums` / `MovementAmplitudeEnums`）
- `Authorization: Token` 统一鉴权
- 单元测试 `ViduClientTest`（当前被 `skipTests` 关闭）

### 已知问题 / Known
- 测试配置 `application.yaml` 含明文 API Key（见 SECURITY.md 风险 #1）
- 依赖内部 `common-utils` 导致外部无法直接构建（见 SECURITY.md 风险 #2）
- `pom.xml` 暴露内部 nexus 与 gpg 指纹（见 SECURITY.md 风险 #3）
