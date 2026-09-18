# 安全策略 / Security Policy

## 🛡 受支持的版本 / Supported Versions

| 版本 | 是否维护 |
| --- | --- |
| `0.0.1-SNAPSHOT` | ✅ 开发中（最新） |

## 🔒 漏洞上报 / Reporting a Vulnerability

请**不要**在公开 Issue 中披露安全漏洞。可通过以下方式私下上报：

- 使用 GitHub 仓库的 **Security → Report a vulnerability**（安全通告）
- 或邮件联系维护者：**wangyb@yunlou.net.cn**

请尽量提供：漏洞类型与影响范围、复现步骤、可能的修复建议。我们会在收到后尽快确认并回应。

## 🔴 已知安全风险 / Known Security Risks

### 1. 测试配置明文 API Key 泄露（最高优先级）
`src/test/resources/application.yaml` 中**明文提交了真实可用的 Vidu API Key**：

```
vidu:
  sdk:
    domain: https://api.vidu.zone
    api-version: 1.0.0
    api-key: vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s
```

该密钥对应测试域 `api.vidu.zone`，已对公众暴露，等同泄露。

**立即处置**：
1. **前往 Vidu 控制台撤销 / 重新生成该 API Key**，旧值作废；
2. 将 `application.yaml` 中的 `api-key` 改为占位符（如 `vda_xxxxxx`），并建议把该文件加入 `.gitignore`、提供 `application.yaml.example`；
3. 若历史提交已含该密钥，需通过 `git filter-repo` 或强制重写历史清除，避免他人从历史中恢复；
4. 业务代码通过 `ViduClientConfig.setApiKey(...)` 从环境变量注入，不要硬编码。

> 注：`ViduClientConfigUtils` 为内存注入式（不读取该 yaml），`ViduClientTest` 使用占位符 `vda_XXXXX`，故该 yaml 目前未被运行代码引用；但**文件存在于公开仓库即构成泄露**，仍须撤销。

### 2. 内部依赖 `common-utils` 导致无法独立构建（供应链 / 可用性）
SDK 依赖 `cn.net.yunlou:common-utils:1.0.0`（提供 `OkHttp3ClientUtils` / `JsonUtils` / `ObjectUtils` 等），该依赖**未发布到 Maven Central**。外部用户 clone 后无法直接 `mvn package`，必须先本地安装 `common-utils`。建议将其开源化，或将所需工具类内联进本 SDK。

### 3. `pom.xml` 暴露内部基础设施
`pom.xml` 的 `distributionManagement` 与 `repositories` 指向内部私服 `https://nexus.smartcloudx.com`，并包含 gpg `keyname` 指纹（`99DA5B4DB67F1436C8013AADB53E1A24F130D3B2`）。公开仓库暴露了内网地址与发布身份；若计划发布到 Maven Central，建议改用 `central-publishing-maven-plugin` 并移除内部 nexus 配置。

### 4. 单元测试被关闭
`pom.xml` 中 `maven-surefire-plugin` 配置 `<skipTests>true</skipTests>`，全部测试被跳过，质量无法保证。建议打开测试并补充 CI。

### 5. 响应字段映射隐患
`ViduResponse` 仅部分字段标注了 `@JsonProperty`（如 `id` / `err_code` / `movement_amplitude` / `off_peak` / `created_at`），而 `state` / `images` / `prompt` / `duration` / `resolution` 等字段直接按 Java 字段名映射。若 Vidu API 返回为下划线风格（`state` 实为 `state`、`images` 为 `images` 等），可能与官方文档不一致，导致解析为空。生产前建议逐一核对并补齐 `@JsonProperty`。

### 6. 缺请求超时 / 重试 / 限流
SDK 未封装 HTTP 超时、重试与限流；高频调用易触发 Vidu 侧限流或连接挂起。建议在外层补充。

---

感谢你帮助提升本项目的安全性！🙏
