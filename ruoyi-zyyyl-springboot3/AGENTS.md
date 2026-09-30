<!-- generated-by: project-guardrails | 2026-09-30 | 继承根目录 AGENTS.md -->

# 智颐云养老后端开发约束

本文档继承根目录 `AGENTS.md` 的全部约束，只补充后端专属规则。

## 一、工作区与模块职责

```text
ruoyi-zyyyl-springboot3/
├─ pom.xml
├─ zyyyl-admin/
├─ zyyyl-common/
├─ zyyyl-framework/
├─ zyyyl-system/
└─ zyyyl-modules/
   └─ zyyyl-module-nursing/          # 新增养老业务模块
```

| 模块 | 职责 | 允许依赖 | 禁止事项 |
| --- | --- | --- | --- |
| `zyyyl-admin` | 启动类、Web Controller、资源文件、Liquibase | `zyyyl-framework`、`zyyyl-system`、`zyyyl-module-nursing` | 跨表业务逻辑、直接访问 Mapper |
| `zyyyl-common` | 工具类、常量、异常、`AjaxResult`、`TableDataInfo` | 无业务模块 | Spring Web 业务、护理业务 |
| `zyyyl-framework` | Spring Security、数据源、异常处理、Web 基础设施 | `zyyyl-common` | 护理业务 Service |
| `zyyyl-system` | 用户、角色、菜单、字典、日志 | `zyyyl-framework` | 老人、入住、护理业务 |
| `zyyyl-module-nursing` | 老人、床位、入住、合同、健康、护理、预约、家属端和 AI | `zyyyl-common`、`zyyyl-framework`、`zyyyl-system` | 修改通用安全规则、复制系统管理 |

新增护理代码统一放在：

```text
zyyyl-modules/zyyyl-module-nursing/src/main/java/com/zyyyl/nursing/
```

禁止继续向 `zzyl/` 写业务代码。`zzyl/zzyl-nursing-platform` 只能用于对照迁移。

## 二、技术基线

| 项目 | 版本或规则 |
| --- | --- |
| Java | `21` |
| Spring Boot | `3.5.13` |
| MyBatis-Flex | `1.11.5`，使用 `mybatis-flex-spring-boot3-starter` |
| Druid | `1.2.27`，使用 `druid-spring-boot-3-starter` |
| Liquibase | 由 Spring Boot BOM 管理 |
| Springdoc | `2.8.14` |
| Knife4j | `4.5.0` |
| JJWT | `0.13.0` |
| MySQL Connector | `8.3.0` |
| MySQL | `8.0`，数据库名 `zyyyl` |
| Spring Cache | 按环境使用 simple 或 Redis |
| 文件存储 | 本地磁盘 + 阿里云 OSS，可配置切换 |
| AI | Spring WebClient 调 Dify，Dify 编排 RAGFlow |

硬约束：

- 不得把 `spring-boot.version` 改为 `4.x`。
- 不得引入 `mybatis-flex-spring-boot4-starter`。
- 不得同时把 MyBatis-Plus 和 MyBatis-Flex 作为主 ORM。
- BOM 已管理的依赖不得在子模块重复写版本号。
- 新依赖必须说明用途、替代方案和许可证，并登记到 `plan/待确认项.md`。

环境默认值：

```text
MySQL: 192.168.100.168:3306/zyyyl
Redis: 192.168.100.168:6379
Dify:  http://192.168.100.128/v1
```

违反示例：

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    <version>3.5.2</version>
</dependency>
```

除非计划明确需要且经确认，否则这是禁止项，因为新项目主 ORM 是 MyBatis-Flex。

## 三、分层与职责边界

### 3.1 Controller

Controller 只负责：

- 接收和校验参数。
- 调用 Service。
- 使用 `AjaxResult`、`TableDataInfo` 或原接口使用的 `R<T>` 组装响应。
- 标注权限、日志和接口文档注解。

禁止：

- Controller 注入 Mapper。
- Controller 编写跨表事务。
- Controller 拼装复杂 SQL。
- Controller 直接返回数据库实体中的敏感字段。

违反示例：

```java
@Autowired
private CheckInMapper checkInMapper;
```

护理业务正确入口示例：

```java
@RestController
@RequestMapping("/nursing/checkIn")
public class CheckInController extends BaseController {
    private final ICheckInService checkInService;
}
```

### 3.2 Service

- 业务规则、跨表编排、状态流转和事务放在 Service。
- `@Transactional` 只标在 Service 的 public 方法。
- 同类内部调用带事务的方法时必须通过代理或拆分服务。
- 床位占用、预约上限和护理任务生成必须处理并发或幂等。

护理入住事务以 `ICheckInService.apply(CheckInApplyDto dto)` 为边界。禁止把床位更新、合同创建和入住记录创建拆成三个 Controller 分别提交。

### 3.3 Mapper 与 XML

- SQL 和 ORM 调用只放在 Mapper。
- Mapper XML 放在 `zyyyl-module-nursing/src/main/resources/mapper/nursing/`。
- XML namespace 必须指向对应的 `com.zyyyl.nursing.mapper.*`。
- 新 SQL 禁止 `SELECT *`。
- 动态 SQL 必须使用 `#{}` 参数绑定，禁止 `${}` 拼接用户输入。

MyBatis-Plus 到 MyBatis-Flex 的迁移规则：

| 原实现 | 新实现 |
| --- | --- |
| `BaseMapper<T>` | MyBatis-Flex `BaseMapper<T>` |
| `ServiceImpl<M,T>` | MyBatis-Flex `ServiceImpl<M,T>` |
| `LambdaQueryWrapper` | `QueryWrapper.create().where(...)` |
| `LambdaUpdateWrapper` | `UpdateWrapper.create().set(...)` |
| `selectPage` / `Page` | 基准项目分页约定或 MyBatis-Flex 分页 |
| `Wrappers.<T>lambdaQuery()` | `QueryWrapper.create()` |

禁止以“改动太大”为理由把整个护理模块保留为 MyBatis-Plus。

## 四、实体、DTO 与 VO

- 数据库实体放在 `domain/`。
- 入参 DTO 放在 `dto/`。
- 出参 VO 放在 `vo/`。
- Controller 不直接接收或返回包含密码、内部标记的实体。
- 对外字段保持原接口字段名，内部可转为 VO。

违反示例：`FamilyMember` 包含密码哈希，Controller 直接返回 `R<FamilyMember>`。

## 五、异常与日志

- 业务异常使用项目统一异常类型，并由 `GlobalExceptionHandler` 转换为 `AjaxResult`。
- 禁止空 `catch`。
- 禁止只调用 `e.printStackTrace()`。
- `error` 日志必须带业务标识，如 `elderId`、`checkInId`、`taskId`。
- 身份证号、手机号、密码和 Token 不得完整写入日志。

违反示例：

```java
catch (Exception e) {
    e.printStackTrace();
}
```

## 六、配置与依赖

- 环境地址写进 `application-*.yml` 并通过环境变量覆盖。
- `application-data.yml` 不得提交真实生产密码、OSS AccessKey 或 Dify Token。
- 主数据源保持 `MASTER`，当前项目默认单机部署，不引入多活数据源。
- 新增配置必须有默认配置、说明和启动验证。
- 配置前缀统一使用 `zyyyl`，不得新增本地 `geek` 配置项。

当前默认端口为 `8080`。若改为其他端口，计划文件、Vite 代理、小程序环境变量和部署文档必须在同一次改动中同步。

### 6.1 文件存储

- `STORAGE_TYPE=local` 时使用本地磁盘，默认目录为 `D:/zyyyl/uploadPath`。
- `STORAGE_TYPE=oss` 时必须配置 AccessKey、Secret、Bucket 和 Endpoint。
- OSS 参数缺失时自动回退本地并记录 warn 日志，不得启动失败。

### 6.2 AI 服务

- Java 后端只访问 Dify API，不直接调用 RAGFlow。
- Dify 通过受控 `/dify/serve/*` 接口查询业务数据。
- Dify Token 和微信 AppSecret 只从环境变量读取。

## 七、安全

- 登录、密码、JWT 和权限只使用 `zyyyl-framework` 的现有实现。
- 权限标识保持 `nursing:<资源>:<动作>` 形式，例如 `nursing:checkIn:apply`。
- 家属端查询老人信息前必须校验 `family_member_elder` 绑定关系。
- 文件上传统一走 `/file/**` 和存储适配层，不另起上传实现。
- 文件扩展名白名单、大小上限和随机文件名必须同时校验。

违反示例：使用 `file.getOriginalFilename()` 直接作为 `D:/zyyyl/uploadPath/` 下的保存路径。

## 八、数据库与 Liquibase

Liquibase 目录：

```text
zyyyl-admin/src/main/resources/db/changelog/
```

新增护理业务 changeSet 建议文件名：

```text
changelog-5-zyyyl-nursing.xml
```

要求：

- changeSet ID 使用 `日期-序号-说明`，如 `20260930-01-create-nursing-task-index`。
- 已执行 changeSet 不得修改，只能追加新 changeSet。
- 新增索引使用 `preConditions` 防止重复创建。
- 回滚必须能删除本次新增索引、列或表，不破坏原业务数据。

禁止修改 `zzyl/sql/zzyl.sql`。

## 九、构建与自测

本机 Maven 默认可能使用 JDK 17，构建前显式设置 JDK 21：

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-21'
mvn -B -DskipTests compile
```

模块级验证：

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-21'
mvn -B -pl zyyyl-modules/zyyyl-module-nursing -am test
```

后端任务完成的最低证据：

- `mvn compile` 成功。
- 受影响接口至少执行一次请求验证。
- 数据库 changeSet 在空库和旧库快照上均有执行记录。
- 启动日志无数据源、Security、Mapper 和 Liquibase 异常。

## 十、会话恢复流程

新会话开始或 `/clear` 后：

1. 读根目录 `AGENTS.md`。
2. 读本文件。
3. 读 `plan/总体协调计划.md`、`plan/后端-plan.md`、`plan/待确认项.md`。
4. 报告当前阶段、已完成任务卡、下一任务和阻塞项。
5. 等用户确认后继续。
