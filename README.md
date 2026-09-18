# vidu-java-sdk

> 基于 Java 的 [Vidu](https://www.vidu.cn) AI 视频生成开放 API（v2）客户端 SDK，覆盖文生视频、图生视频、参考生视频、首末帧生视频、模板生视频与任务查询，封装鉴权、请求校验与响应解析。
>
> A Java client SDK for the Vidu AI video-generation Open API (v2) — text-to-video, image-to-video, reference-to-video, start/end-to-video, template-to-video, and task query, with auth, validation and response parsing built in.

## 特性 / Features

- 🎬 **文生视频** `text2Video` — 根据文本提示词生成视频
- 🖼 **图生视频** `img2Video` — 基于单张参考图生成视频
- 🔗 **参考生视频** `reference2Video` — 最多 7 张参考图
- 🎞 **首末帧生视频** `startEnd2Video` — 指定首帧与尾帧
- 🧩 **模板生视频** `template2Video` — 使用内置模板（如 Hugging）
- 🔍 **任务查询** `getTaskApi(taskId)` — 按任务 ID 查询生成状态与结果
- 🔐 **统一鉴权** — 以 `Authorization: Token <apiKey>` 透传
- ✅ **参数校验** — 模型 / 时长 / 分辨率 / 比例 / 风格 等枚举前置校验，错误即时抛出 `ViduException`

## 架构 / Architecture

```mermaid
flowchart LR
    App[调用方 / Spring 应用] -->|ViduClientConfigUtils.putClientConfig| Cfg[(内存配置 ConcurrentHashMap + ThreadLocal)]
    App --> Client[ViduClient @Component]
    Client -->|Authorization: Token| HTTP[OkHttp3ClientUtils]
    HTTP -->|POST /ent/v2/*| API[Vidu API https://api.vidu.cn]
    HTTP -->|GET /ent/v2/tasks/{id}/creations| API
    API -->|ViduResponse JSON| Client
    Client --> App
```

| 模块 | 说明 | 关键类 |
| --- | --- | --- |
| 客户端 | 6 个生成/查询方法 | `ViduClient` |
| 配置 | API Key / 域 / 版本，内存注入 | `ViduClientConfig` / `ViduClientConfigUtils` |
| 请求 | 5 种生成请求体 | `Text2VideoViduRequest` 等（继承 `ViduRequest`） |
| 响应 | 任务态与生成物 | `ViduResponse` / `Creation` |
| 枚举 | 模型 / 时长 / 分辨率 / 比例 / 风格 / 模板 / 运动幅度 | `sdk/enums/*` |
| 常量 | API 域与端点 | `sdk/constant/ViduConstant` |

## 技术栈 / Tech Stack

| 组件 | 版本 / 说明 |
| --- | --- |
| Java | 8 |
| Vidu Open API | v2（`https://api.vidu.cn/ent/v2/...`） |
| HTTP | OkHttp3（`OkHttp3ClientUtils`） |
| JSON | Jackson（`@JsonProperty` 映射） |
| 工具 | Lombok、Guava、Hibernate Validator（`jakarta.validation-api`） |
| 容器 | Spring（`@Component`，可作 bean 注入） |

## 安装 / Install

```xml
<dependency>
    <groupId>cn.net.yunlou</groupId>
    <artifactId>vidu-java-sdk</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

> ⚠️ **构建依赖说明**：本 SDK 依赖内部组件 `cn.net.yunlou:common-utils:1.0.0`（提供 `OkHttp3ClientUtils`、`JsonUtils`、`ObjectUtils` 等）。该依赖**未发布到 Maven Central**，外部直接 `mvn package` 会失败。需先将 `common-utils` 安装到本地仓库（`mvn install:install-file`）或发布到可访问的仓库后，方可构建。长期建议将 `common-utils` 开源化或把所需工具类内联进本 SDK。

## 快速开始 / Quick Start

### 1. 配置 API Key（务必从环境变量注入，勿硬编码）

```java
// 建议：API Key 从环境变量读取，切勿提交进代码/配置
ViduClientConfig clientConfig = new ViduClientConfig();
clientConfig.setApiKey(System.getenv("VIDU_API_KEY"));
clientConfig.setApiVersion("1.0.0");
ViduClientConfigUtils.putClientConfig(clientConfig);
```

> `ViduClient` 标注了 Spring `@Component`，在 Spring 应用中可直接 `@Autowired`；非 Spring 环境则 `new ViduClient()` 即可。

### 2. 文生视频

```java
ViduClient viduClient = new ViduClient();   // 或用注入的 bean

Text2VideoViduRequest request = new Text2VideoViduRequest();
request.setModel(ModelEnums.VIDUQ1.getValue());   // "viduq1"
request.setStyle("general");
request.setPrompt("一位宇航员在雾中行走，背景是迷人的白金灯光……");
request.setDuration(5);                            // 可选 4 / 5 / 8
request.setResolution("1080p");                    // 360p / 720p / 1080p
request.setAspectRatio(AspectRatioEnums.R16_9.getValue()); // "16:9"
request.setOffPeak(false);

ViduResponse response = viduClient.text2Video(request);
System.out.println("taskId=" + response.getTaskId());
```

### 3. 查询任务结果

```java
ViduResponse task = viduClient.getTaskApi(response.getTaskId());
System.out.println("state=" + task.getState());
List<Creation> creations = task.getCreations();   // 生成完成的视频列表
```

### 4. 其他生成方式（方法签名一致）

| 方法 | 请求体 | 关键参数 |
| --- | --- | --- |
| `img2Video` | `Img2VideoViduRequest` | 单张图 `images`（1 张） |
| `reference2Video` | `Reference2VideoViduRequest` | 参考图 `images`（≤7 张） |
| `startEnd2Video` | `StartEnd2VideoViduRequest` | 首/尾图 `images`（2 张） |
| `template2Video` | `Template2VideoViduRequest` | 模板 `template`（见 `TemplateEnums`）、`watermark` |

## 请求字段（以文生视频为例）/ Request Fields

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| `model` | String | `viduq1` / `vidu1.5` / `vidu2.0` 等（见 `ModelEnums`） |
| `style` | String | `general`（默认）/ `anime` |
| `prompt` | String | 文本提示词，≤1500 字符 |
| `duration` | Integer | 时长，依模型可选 4 / 5 / 8 |
| `seed` | Integer | 随机种子，0 或空则随机 |
| `aspect_ratio` | String | `16:9` / `9:16` / `1:1` |
| `resolution` | String | `360p` / `720p` / `1080p` |
| `movement_amplitude` | String | `auto` / `small` / `medium` / `large` |
| `bgm` | Boolean | 是否自动添加背景音乐，默认 false |
| `off_peak` | Boolean | 错峰生成，默认 false |
| `callback_url` | String | 任务状态变更回调地址（POST） |

## 响应结构 / Response

`ViduResponse` 关键字段：`taskId`(id)、`state`、`errCode`、`model`、`images`、`prompt`、`duration`、`seed`、`resolution`、`bgm`、`movementAmplitude`、`payload`、`offPeak`、`credits`、`createdAt`、`creations`（生成完成的 `Creation` 列表）。

## 安全须知 / Security

- 🔴 **测试配置明文 API Key 泄露**（最高优先级，见上方警示与 [SECURITY.md](./SECURITY.md)）。
- 不要在源码或配置中硬编码密钥；通过 `ViduClientConfig` 注入。
- SDK 当前无请求超时 / 重试 / 限流封装，生产调用建议在外层补充。

## 路线图 / Roadmap

### ✅ 已完成 / Done
- [x] 五大生成场景 + 任务查询客户端封装
- [x] 模型 / 时长 / 分辨率 / 比例 / 风格 参数枚举与前置校验
- [x] `Authorization: Token` 统一鉴权

### 📋 待办 / TODO
- [ ] **撤销并移除 `application.yaml` 中明文 API Key，加入 `.gitignore`**
- [ ] 密钥改为环境变量 / 配置中心注入，提供 `application.yaml.example`
- [ ] 补 `OkHttp3ClientUtils` 超时 / 重试 / 限流
- [ ] 修复 `ViduResponse` 部分字段缺少 `@JsonProperty` 的映射隐患
- [ ] 启用被 `skipTests` 关闭的单元测试，补充 CI

## 许可证 / License

[Apache License 2.0](./LICENSE)
