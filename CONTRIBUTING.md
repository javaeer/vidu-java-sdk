# 贡献指南 / Contributing

感谢你考虑为 **vidu-java-sdk** 做出贡献！

## 🌿 分支模型 / Branch Model

- `master`：默认分支，稳定可发布状态
- `dev`：日常开发集成分支
- `feature/*`：新功能（从 `dev` 切出）
- `fix/*`：缺陷修复（从 `dev` 切出）

> 提交 PR 请指向 `dev`（或 `master`）。

## 📝 提交规范 / Commit Convention

请遵循 [Conventional Commits](https://www.conventionalcommits.org/)：

```
<type>(<scope>): <subject>

type: feat | fix | docs | refactor | test | chore | security
示例：
  feat: 支持 vidu2.0 模型
  fix: 修复 response 字段映射缺失 @JsonProperty
  security: 移除测试配置明文 API Key
```

## 🛠 本地开发 / Local Development

1. JDK 8 + Maven 3.6+
2. 不建议提交真实 API Key；通过 `ViduClientConfig.setApiKey(System.getenv("VIDU_API_KEY"))` 注入
3. 运行测试前，先清理 `src/test/resources/application.yaml` 中的真实密钥

## ✅ PR 检查清单

- [ ] 代码通过本地编译（`mvn clean compile`）
- [ ] 新增/修改行为有测试覆盖（当前 `surefire` 跳过测试，请打开）
- [ ] 不引入明文密钥 / 内网地址
- [ ] 更新相关文档（README / ARCHITECTURE / SECURITY）
- [ ] 遵循提交规范

## 🔒 安全漏洞

请勿在公开 Issue 披露安全问题，按 [SECURITY.md](./SECURITY.md) 私下上报。
