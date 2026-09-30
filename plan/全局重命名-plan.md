<!-- generated-by: project-guardrails | 2026-09-30 | 全局 geek → zyyyl 执行计划 -->

# 智颐云养老全局重命名计划

> 本文档是正式迁移业务前的强制前置阶段。必须单独完成后编译、启动和 Git 验证。

## 当前进度

| 项 | 内容 |
| --- | --- |
| 当前阶段 | 前置阶段 R：完成，等待用户确认进入后端阶段一 |
| 最后更新 | 2026-09-30 |
| 下一项任务 | 后端阶段一：Spring Boot 3 骨架与接口冻结 |

## 一、最终命名映射

| 原名称 | 新名称 |
| --- | --- |
| `ruoyi-geek-springboot3` | `ruoyi-zyyyl-springboot3` |
| `ruoyi-geek-vue3` | `ruoyi-zyyyl-vue3` |
| `zzyl-family-uniapp` | `zyyyl-family-uniapp` |
| `geek` | `zyyyl` |
| `geek-admin` | `zyyyl-admin` |
| `geek-common` | `zyyyl-common` |
| `geek-framework` | `zyyyl-framework` |
| `geek-system` | `zyyyl-system` |
| `geek-modules` | `zyyyl-modules` |
| `geek-module-nursing` | `zyyyl-module-nursing` |
| `com.geekxd` | `com.zyyyl` |
| `com.geek` | `com.zyyyl` |
| `GeekApplication` | `ZyyylApplication` |
| `GeekServletInitializer` | `ZyyylServletInitializer` |
| `geek.version` | `zyyyl.version` |
| `geek:` 配置前缀 | `zyyyl:` |
| `zzyl` 数据库名 | `zyyyl` |

外部地址保持原样：

- Gitee 上游仓库地址。
- GitHub 远程仓库已经是 `zyyyl`。
- 第三方依赖的原始坐标和许可证 URL。

## 二、任务卡

### R-01 重命名前备份

- [x] 备份当前 `ruoyi-geek-springboot3` Git 仓库和 `.git` 到 `D:\develop\code\zyyyl-backup-20260930`。
- [x] 记录原始上游 `https://gitee.com/geek-xd/ruoyi-geek-springboot.git`、提交 `31c5646` 和分支 `springboot3-work`。
- [x] 已记录本地目录名、Maven 坐标和包名的最终映射。
- [x] 工作区仅有未跟踪的 `AGENTS.md`，已随完整目录一起备份。

**验收**：备份可恢复，映射清单完整。

### R-02 根目录结构重命名

- [x] `ruoyi-geek-springboot3` → `ruoyi-zyyyl-springboot3`。
- [x] `ruoyi-geek-vue3` → `ruoyi-zyyyl-vue3`。
- [x] 家属端工作区已使用 `zyyyl-family-uniapp`。
- [x] 参考目录整理为 `reference/zzyl` 和 `reference/zzyl-app`。

**验收**：根目录结构与 `AGENTS.md` 一致。

### R-03 Maven 坐标重命名

- [x] 根 `pom.xml` 的 artifactId `geek` → `zyyyl`。
- [x] groupId `com.geekxd` → `com.zyyyl`。
- [x] 模块 `geek-*` → `zyyyl-*`。
- [x] `geek.version` → `zyyyl.version`。
- [x] 子模块之间的依赖坐标同步。

**验收**：`mvn help:effective-pom` 不引用本地 `geek-*` 坐标。

### R-04 Java 包和目录重命名

- [x] `com/geek` → `com/zyyyl`。
- [x] `com.geek` → `com.zyyyl`。
- [x] `GeekApplication` → `ZyyylApplication`。
- [x] `GeekServletInitializer` → `ZyyylServletInitializer`。
- [x] `GeekConfig` → `ZyyylConfig`，`GeekStorageBucket` → `ZyyylStorageBucket`。
- [x] 所有 import、Mapper namespace 和配置类同步。
- [x] 启动类 `scanBasePackages` 改为 `com.zyyyl`。

**验收**：源码中不再出现本地 `com.geek`。

### R-05 配置和资源重命名

- [x] `geek:` 配置前缀改为 `zyyyl:`。
- [x] `geek.sh` 改为 `zyyyl.sh`。
- [x] `application*.yml` 的应用名、文件路径和日志包名改名为 `zyyyl`。
- [x] 数据库 URL 改为 `zyyyl`。
- [x] 文件存储默认路径改为 `D:/zyyyl/uploadPath`。
- [x] 敏感配置改为环境变量占位符。
- [x] 外部 Gitee URL 和 `RuoYi-Geek` 文档名称保持不变。

**验收**：本地配置中不再出现 `geek` 前缀。

### R-06 前端重命名

- [x] 管理端目录改为 `ruoyi-zyyyl-vue3`。
- [x] `package.json` 名称改为 `ruoyi-zyyyl-vue3`。
- [x] 品牌标题改为“智颐云养老”。
- [x] 保留 `@ruoyi/core`、`@ruoyi/ui` 这类非 geek 包名。
- [x] 更新 Vite 代理到 `8080`、开发端口到 `5173`。

**验收**：`pnpm build:prod` 成功，输出无旧品牌。

### R-07 小程序重命名

- [x] 工作区使用 `zyyyl-family-uniapp`。
- [x] 应用名称统一为“智颐云养老”。
- [x] API 环境变量使用 `zyyyl` 命名。
- [ ] 从 `reference/zzyl-app` 恢复页面，不直接编辑编译产物；该验证延后至家属端阶段五，不阻塞重命名基线。

**验收**：微信开发者工具可编译首页。

### R-08 数据库重命名

- [x] 从 `reference/zzyl/sql/zzyl.sql` 提取 DDL。
- [x] 将 `zzyl` schema 改为 `zyyyl`。
- [x] 清理全部 INSERT 和真实个人数据。
- [x] 已在 MySQL 8.0 执行 `zyyyl` 业务表结构初始化（19 张养老业务表；`sys_*` 由 Liquibase 管理）。
- [ ] 脱敏演示数据的生成与导入延后至后端阶段六。
- [ ] 将 DDL 转换为 Liquibase changeSet，安排在后端阶段六。

**验收**：MySQL 8.0 可创建 `zyyyl` 并完成初始化。

### R-09 全局残留检查

```powershell
rg -n -S "com\.geek|geek-admin|geek-common|geek-framework|geek-system|geek-module|ruoyi-geek-springboot3|ruoyi-geek-vue3|zzyl-family-uniapp" .
```

允许出现的例外：

- `RuoYi-Geek` 上游名称说明。
- `https://gitee.com/geek-xd/` 远程地址。
- `reference/zzyl` 历史参考目录和脚本名称。

**验收**：所有命中项都能解释为外部地址、上游作者或历史参考。

### R-10 编译和提交

- [x] JDK 21 执行 Maven 全模块编译和打包成功。
- [x] 管理端执行生产构建成功。
- [x] 后端执行启动和登录冒烟测试通过：MySQL、Redis、Liquibase 均正常，`/login`、`/getInfo`、`/getRouters` 返回 200。
- [x] Git 提交已完成：`3f5b77c refactor: rename local geek identifiers to zyyyl`、`332f208 docs(plan): record rename verification status`、`5a3ce26 fix(backend): finish zyyyl baseline startup`。
- [x] 已推送 `origin/main`；GitHub 密钥扫描拦截后，已将仅作本地迁移参考的 `reference/` 从 Git 历史移除并加入忽略规则。

说明：`reference/` 仍保留在本机工作区，未提交到远程仓库；重写前历史已备份为 `D:\develop\code\zyyyl-before-secret-scrub.bundle`。

**验收**：重命名阶段单独提交，业务迁移尚未混入。

## 三、禁止事项

- 禁止在重命名提交中同时迁移护理业务代码。
- 禁止重命名外部 Gitee 地址和第三方依赖来源。
- 禁止对数据库真实数据做不可逆批量改名。
- 禁止删除备份后才开始替换。

## 四、进度日志

| 时间 | 任务卡 | 事件 | 关键信息 |
| --- | --- | --- | --- |
| 2026-09-30 | R-00 | 计划创建 | 根据用户最终命名决策生成 |
| 2026-09-30 | R-01 | 已完成 | 后端 Git 和项目文档备份到 `D:\develop\code\zyyyl-backup-20260930` |
| 2026-09-30 | R-02 | 已完成 | 三端目录和参考目录完成重命名 |
| 2026-09-30 | R-03 | 已完成 | Maven 坐标和模块名统一为 `zyyyl` |
| 2026-09-30 | R-04 | 已完成 | Java 包、启动类、配置类和存储桶类完成重命名 |
| 2026-09-30 | R-05 | 已完成 | 配置前缀、日志路径和敏感配置占位完成调整 |
| 2026-09-30 | R-06 | 已完成 | 管理端包名、品牌、端口和构建成功 |
| 2026-09-30 | R-07 | 已完成 | 小程序最终工作区和品牌命名完成 |
| 2026-09-30 | R-08 | 部分完成 | 已提取 39 张表 DDL 到 `migration/zyyyl/zyyyl-schema.sql`，数据库执行待 P4 |
| 2026-09-30 | R-09 | 已完成 | 本地旧命名残留检查和例外确认完成 |
| 2026-09-30 | R-10 | 进行中 | Maven 打包和前端构建成功，启动冒烟、Git 提交和推送待执行 |
| 2026-09-30 | R-10 | 部分完成 | 本地提交 `8553b1e` 已创建；GitHub HTTPS 连接被重置，推送待网络恢复 |
| 2026-09-30 | R-10 | 已完成 | MySQL、Redis 和 Liquibase 启动成功；使用临时验证码完成管理员登录，`/login`、`/getInfo`、`/getRouters`、系统菜单查询均返回 200 |
| 2026-09-30 | R-10 | 已完成 | 提交 `5a3ce26` 完成基线启动修复；`reference/` 历史已移除并以本地参考目录和 bundle 备份保留 |
| 2026-09-30 | R-10 | 已完成 | `origin/main` 推送成功，远程分支已建立 |
