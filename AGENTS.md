<!-- generated-by: project-guardrails | 2026-09-30 | 假设与缺口见 plan/待确认项.md -->

# 智颐云养老项目全局约束

本文档是本项目的全局开发约束。开发过程中不修改本文件；执行进度、任务拆分和阶段状态只写入 `plan/`。

## 一、项目概述

智颐云养老系统面向养老机构管理员、护理人员、健康评估人员、家属用户和 AI 助手服务，覆盖老人档案、机构床位、入住合同、健康评估、护理计划、护理任务、预约、家属绑定和 AI 问询等业务。

本项目是在存量养老系统基础上进行技术升级、UI 翻新和品牌统一，不是重新设计业务模型。首要目标是保持原业务完整、接口路径不变、历史数据可迁移。

## 二、技术栈

| 端 | 工作目录 | 技术栈 | 运行时 / 构建 |
| --- | --- | --- | --- |
| 后端 | `ruoyi-zyyyl-springboot3/` | Java 21、Spring Boot 3.5.13、Spring Security、MyBatis-Flex 1.11.5、Druid、Liquibase、JWT | JDK 21、Maven 3.9+ |
| 管理端 | `ruoyi-zyyyl-vue3/` | Vue 3、Vite、Pinia、Vue Router、Element Plus、ECharts、pnpm | Node 20.19+ 或 22.12+ |
| 家属端小程序 | `zyyyl-family-uniapp/` | uni-app、Vue 3、微信小程序 | HBuilderX 或 uni-app CLI |

只读迁移来源：

- `zzyl/`：原 RuoYi 3.8.8 养老业务代码和数据库脚本，只作为迁移参考。
- `zzyl-app/`：当前已编译小程序产物，只作为页面、接口和交互参考。

命名过渡：

- 目录重命名已完成，最终工作区为 `ruoyi-zyyyl-springboot3`、`ruoyi-zyyyl-vue3` 和 `zyyyl-family-uniapp`。
- 后续新增代码、配置和文档不得再使用本地 `geek` 名称。
- 只有外部仓库地址、上游 `RuoYi-Geek` 名称和 `reference/` 迁移资料允许保留历史标识。

禁止把 Spring Boot 4 代码作为开发基线。禁止修改根目录 `pom.xml` 中的 `spring-boot.version` 为 `4.x`，禁止把 `mybatis-flex-spring-boot3-starter` 替换为 Spring Boot 4 专用 starter。

## 三、目录结构与归属

```text
zyyyl/
├─ docs/                         # 需求、原型、技术、数据库、接口、改造文档
├─ plan/                         # 唯一随开发进度修改的任务与状态文件
├─ ruoyi-zyyyl-springboot3/       # Spring Boot 3 后端工作区
├─ ruoyi-zyyyl-vue3/              # 管理端工作区
├─ zyyyl-family-uniapp/           # 家属端 uni-app 源码工作区
├─ zzyl/                         # 原系统迁移参考，只读
└─ zzyl-app/                     # 已编译小程序参考，只读
```

| 目录 | 职责 | 禁止事项 |
| --- | --- | --- |
| `ruoyi-zyyyl-springboot3/zyyyl-admin/` | 启动类、Controller 入口、资源和 Liquibase | 编写跨表业务逻辑 |
| `ruoyi-zyyyl-springboot3/zyyyl-common/` | 通用工具、常量、异常、响应对象 | 引入 Web 业务或护理业务 |
| `ruoyi-zyyyl-springboot3/zyyyl-framework/` | Security、数据源、异常处理、Web 基础设施 | 编写护理业务 Service |
| `ruoyi-zyyyl-springboot3/zyyyl-system/` | 用户、角色、菜单、字典等系统管理 | 承载护理业务 |
| `ruoyi-zyyyl-springboot3/zyyyl-modules/zyyyl-module-nursing/` | 养老业务领域模块 | 修改系统级 Security 规则 |
| `ruoyi-zyyyl-vue3/src/` | 应用壳、全局路由、全局入口 | 堆叠护理业务页面 |
| `ruoyi-zyyyl-vue3/modules/nursing/` | 养老管理端页面、API、业务组件 | 复制 `packages/core` 的请求封装 |
| `zyyyl-family-uniapp/` | 家属端小程序源码 | 修改 `zzyl-app/common/vendor.js` 等编译产物 |

## 四、开发流程

### 4.1 计划先行

任何非平凡改动前，必须先读取：

1. 根目录 `AGENTS.md`
2. 当前端目录的 `AGENTS.md`
3. `plan/总体协调计划.md`
4. 当前端对应的 `plan/*-plan.md`

未出现在当前阶段任务卡中的功能，不得因为“顺手”直接实现。违反示例：用户只要求调整老人列表样式，却顺便修改了入住事务和数据库索引。

### 4.2 阶段确认闸门

每完成一个阶段，必须停下汇报：

- 已完成任务卡编号。
- 修改文件和关键决策。
- 已运行的构建、接口或页面验证命令。
- 未完成项和风险。
- 下一阶段计划。

用户明确确认后，才能进入下一阶段。违反示例：阶段一后端骨架未确认，直接开始迁移护理任务页面。

### 4.3 唯一可写文件

开发过程中只允许更新 `plan/*.md` 的进度区、状态标记和进度日志。根 `AGENTS.md`、三端 `AGENTS.md` 和 `docs/` 均视为冻结约束或参考文档。

违反示例：发现实际接口与文档不一致，直接改 `AGENTS.md`，而不是登记 `plan/待确认项.md` 并等待确认。

### 4.4 计划变更走申请

需要调整阶段、任务范围、技术路线或验收标准时，先向用户说明：

- 变更原因。
- 影响的计划文件。
- 对接口、数据库、前端和进度的具体影响。
- 可选方案。

获得确认后，才能修改 `plan/*.md`。违反示例：把 MyBatis-Flex 悄悄换回 MyBatis-Plus，只在进度日志中写“已调整 ORM”。

### 4.5 文档优先级

冲突时按以下顺序处理：

1. 用户在当前对话中的明确指令。
2. `docs/01-需求文档.md`
3. `docs/02-UI页面原型设计方案.md`
4. `docs/03-技术栈选型文档.md`
5. `docs/04-数据库优化设计文档.md`
6. `docs/05-API接口文档.md`
7. `docs/06-项目整体修改文档.md`
8. 根 `AGENTS.md` 与端 `AGENTS.md`
9. 对话中的口头描述

### 4.6 冲突升级

文档、代码和对话之间发生冲突时，不得自行裁决。必须写入 `plan/待确认项.md` 的“规则冲突”章节，列出冲突双方、证据、影响和可选方案。

违反示例：接口文档写 `/nursing/checkIn/apply`，代码写 `/nursing/checkin/apply`，直接按代码修改前端。

### 4.7 不猜着写

业务规则、接口字段、数据库字段和品牌信息不明确时，先登记待确认；不得在代码中留下“应该如此”的隐式假设。

违反示例：未确认健康评分编码，就把 `healthScore < 60` 直接写成高风险。

### 4.8 进度留痕

每个阶段完成后，在对应计划文件的“进度日志”增加一行：

```text
YYYY-MM-DD | 完成任务卡编号 | 关键决策或验证证据
```

### 4.9 自测前置

只有实际执行过验证才能声明完成。后端至少执行编译；接口改动执行请求验证；前端执行构建；小程序执行开发者工具预览。

违反示例：Controller 写完但项目无法编译，却把任务标记为完成。

### 4.10 已冻结的实施边界

- 数据库 schema 为 `zyyyl`，MySQL 服务位于 `192.168.100.168:3306`。
- Redis 位于 `192.168.100.168:6379`。
- 后端端口为 `8080`，管理端开发端口为 `5173`。
- Dify API 为 `http://192.168.100.128/v1`，RAGFlow HTTP API 端口为 `9380`。
- 小程序新增微信授权登录，但保留原 `/member/user/login`。
- 不接真实微信支付。
- 文件存储支持本地磁盘和阿里云 OSS，未配置 OSS 时默认本地。
- 首期只新增护理任务超时提醒和家属服务进度，规则型风险提示只预留数据结构。
- 首期按单机构设计，不引入租户隔离。

## 五、接口契约

### 5.1 路径冻结

现有业务接口路径保持不变，以 `docs/05-API接口文档.md` 为索引。重点前缀包括：

- 管理端登录与系统：`/login`、`/getInfo`、`/getRouters`、`/system/*`
- 老人与机构：`/nursing/elder/*`、`/nursing/floor/*`、`/nursing/bed/*`
- 入住与合同：`/nursing/checkIn/*`、`/nursing/contract/*`
- 健康与护理：`/nursing/healthAssessment/*`、`/nursing/nursingTask/*`
- 家属端：`/member/user/*`、`/member/reservation/*`、`/member/orders/*`
- AI：`/ai/*`、`/dify/serve/*`

禁止新增 `/api/v1` 前缀，禁止把驼峰资源名改成 kebab-case。违反示例：把 `/nursing/checkIn/apply` 改为 `/api/v1/check-in/apply`。

### 5.2 响应结构

保留原接口使用的响应类型：

- 单条操作使用 `AjaxResult`。
- 管理端分页列表使用 `TableDataInfo<T>` 和 `getDataTable(...)`。
- 原家属端使用 `R<T>` 的接口继续返回相同结构。

禁止为了“统一风格”改变已有接口响应字段。违反示例：把 `TableDataInfo` 的 `rows`、`total` 改成 `data.list`、`data.total`。

### 5.3 分页与时间

- 管理端分页沿用原接口的 `pageNum`、`pageSize`。
- 家属端预约分页沿用 `pageNum`、`pageSize`、`status`。
- 日期时间统一使用 `yyyy-MM-dd HH:mm:ss`，日期使用 `yyyy-MM-dd`。

### 5.4 鉴权

- 管理端和家属端 Token 放在 `Authorization` 请求头，格式为 `Bearer <token>`。
- 管理端权限使用 `@PreAuthorize("@ss.hasPermi('nursing:checkIn:apply')")` 形式。
- 家属端必须校验 `family_member_elder` 绑定关系。

违反示例：只检查请求参数中的 `elderId` 是否登录用户上传，而未验证该家属是否绑定老人。

### 5.5 接口文档同步

修改已有接口或新增接口时，必须在同一次改动中更新 `docs/05-API接口文档.md` 和对应前端 API 文件。不得只改 Controller。

## 六、数据库约束

### 6.1 迁移方式

- 新结构与数据变更统一写入 `ruoyi-zyyyl-springboot3/zyyyl-admin/src/main/resources/db/changelog/` 下的 Liquibase changeSet。
- `zzyl/sql/zzyl.sql` 只作为旧库参考，禁止继续修改。
- 每个 changeSet 必须提供可执行的 `rollback`。

违反示例：在 Navicat 手工增加 `nursing_task.timeout_status`，代码仓库没有对应 changeSet。

### 6.2 兼容性

- 第一阶段禁止重命名或删除已有表和列。
- 新增字段必须允许历史数据兼容，必要时先 nullable、回填、再加约束。
- 唯一索引必须先去重，再执行创建脚本。

### 6.3 命名和数据

- 表和列使用 `snake_case`，Java 字段使用 `camelCase`。
- 金额使用 `DECIMAL`，日期使用 `DATE`，时间使用 `DATETIME`。
- 业务表必须包含 `create_by`、`create_time`、`update_by`、`update_time`，按需增加 `remark`。
- 新 SQL 禁止 `SELECT *`。

### 6.4 查询与事务

- 高频条件必须有匹配索引，优先使用组合索引。
- 事务边界放在 Service 的公开方法上。
- 事务中禁止调用 HTTP、Dify、OSS 等远程服务。
- 床位入住、预约人数和护理任务生成必须做并发或幂等校验。

## 七、安全约束

- 密码只存 BCrypt 哈希，禁止明文、MD5、SHA1。
- 数据库、Redis、OSS、微信和 Dify 密钥不得提交到 Git，必须走环境变量或本地未跟踪配置。
- 输入参数必须做类型、范围和长度校验。
- SQL 必须参数化。
- 日志不得输出密码、Token、完整身份证号和完整手机号。
- 文件上传必须限制类型和大小，存储路径不得使用用户提供的文件名。
- 生产环境关闭详细异常堆栈和调试接口。

违反示例：在 `application-data.yml` 中继续提交真实 OSS AccessKey 或 Dify Token。

## 八、协作与交付

- 提交信息使用 `feat(nursing):`、`fix(checkin):`、`docs(plan):`、`refactor(backend):` 等 Conventional Commits 前缀。
- 一次提交只处理一类改动，禁止混入格式化或无关重命名。
- `target/`、`node_modules/`、`dist/`、日志和本地配置不提交。
- 完成功能必须说明验证命令、验证结果和未覆盖范围。
- 修改接口、字段、配置或部署方式时，同步更新对应文档。

## 九、会话恢复流程

新会话开始或执行 `/clear` 后，按以下顺序恢复，不得直接写代码：

1. 读根目录 `AGENTS.md`。
2. 读当前端目录的 `AGENTS.md`。
3. 读 `plan/总体协调计划.md` 和当前端计划文件。
4. 读 `plan/待确认项.md`。
5. 向用户报告当前阶段、已完成任务卡、下一项任务、阻塞项。
6. 等用户确认后继续。

## 十、文档地图

| 文档 | 位置 | 权威性 |
| --- | --- | --- |
| 需求文档 | `docs/01-需求文档.md` | 功能范围与验收标准 |
| UI 原型方案 | `docs/02-UI页面原型设计方案.md` | 页面结构和视觉基线 |
| 技术栈选型 | `docs/03-技术栈选型文档.md` | 技术版本与依赖边界 |
| 数据库优化 | `docs/04-数据库优化设计文档.md` | 表、索引和迁移方向 |
| API 接口 | `docs/05-API接口文档.md` | 接口路径和请求响应基线 |
| 整体修改 | `docs/06-项目整体修改文档.md` | 迁移顺序和品牌清单 |
| 总体计划 | `plan/总体协调计划.md` | 跨端顺序和依赖 |
| 全局重命名 | `plan/全局重命名-plan.md` | `geek` 到 `zyyyl` 的强制前置阶段 |
| 后端计划 | `plan/后端-plan.md` | 后端任务卡与进度 |
| 管理端计划 | `plan/管理端-plan.md` | 管理端任务卡与进度 |
| 家属端计划 | `plan/家属端-plan.md` | 小程序任务卡与进度 |
| 待确认项 | `plan/待确认项.md` | 假设、缺口、冲突、豁免 |
