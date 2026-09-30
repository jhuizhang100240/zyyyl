# 智颐云养老系统 API 接口文档

> 版本：V1.0  
> 基准：原养老系统 Controller 与 RuoYi-Geek 接口规范  
> 兼容原则：接口路径、请求方法和主要参数保持不变  
> 说明：本文档覆盖当前代码可确认的核心接口。未在现有代码中完整出现的接口以“待确认”标记，正式开发前需从旧版接口文档或前端调用代码补齐。

## 1. 通用约定

### 1.1 服务地址

| 环境 | 管理后台后端 | 说明 |
| --- | --- | --- |
| 原系统开发环境 | `http://localhost:9901` | 原 `zzyl` 配置 |
| RuoYi-Geek 开发环境 | `http://localhost:8080` | 克隆标签 `3.9.1-G` 的默认配置 |
| 内网联调环境 | `http://192.168.100.168:8080` | 后端部署在 Linux VM |

AI 环境：

| 服务 | 地址 | 说明 |
| --- | --- | --- |
| Dify API | `http://192.168.100.128/v1` | Java 后端调用入口 |
| RAGFlow HTTP API | `http://192.168.100.128:9380` | 由 Dify 知识库工具调用，Java 不直连 |

接口路径本身不增加 `/api` 或 `/v1` 前缀，继续使用原路径。前端通过这些基础地址拼接请求。

### 1.2 请求格式

| 项目 | 约定 |
| --- | --- |
| 内容类型 | `application/json` |
| 字符编码 | UTF-8 |
| 日期时间 | `yyyy-MM-dd HH:mm:ss` |
| 日期 | `yyyy-MM-dd` |
| 分页参数 | `pageNum`、`pageSize` 或接口原有参数 |
| 路径参数 | 使用原 Controller 的 `{id}`、`{ids}` 形式 |

### 1.3 鉴权

管理端和家属端均使用 Token。

```http
Authorization: Bearer <token>
```

说明：

- HTTP Header 名称不区分大小写。
- 原小程序使用小写 `authorization`，服务端可直接兼容。
- 公开接口包括登录、验证码和部分房型查询。
- 家属端老人数据必须校验家属与老人的绑定关系。

### 1.4 通用响应

#### 普通响应

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 分页响应

```json
{
  "code": 200,
  "msg": "查询成功",
  "rows": [],
  "total": 0
}
```

#### R 泛型响应

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

#### 错误响应

```json
{
  "code": 500,
  "msg": "当前时段已约满",
  "data": null
}
```

### 1.5 错误码

| 状态码 | 说明 |
| --- | --- |
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录、Token 无效或 Token 过期 |
| 403 | 无权限 |
| 404 | 接口或资源不存在 |
| 409 | 数据冲突，例如编号重复 |
| 422 | 业务规则校验失败 |
| 500 | 服务端错误 |
| 503 | 第三方服务不可用，例如 Dify 或存储服务 |

## 2. 管理端接口清单

### 2.1 登录与系统管理

| 方法 | 路径 | 说明 | 鉴权 |
| --- | --- | --- | --- |
| POST | `/login` | 管理员登录 | 否 |
| GET | `/getInfo` | 获取当前用户信息 | 是 |
| GET | `/getRouters` | 获取菜单路由 | 是 |
| POST | `/logout` | 退出登录 | 是 |
| GET | `/captchaImage` | 获取验证码，路径待确认 | 否 |
| GET | `/system/user/list` | 用户列表 | 是 |
| POST | `/system/user` | 新增用户 | 是 |
| PUT | `/system/user` | 修改用户 | 是 |
| DELETE | `/system/user/{ids}` | 删除用户 | 是 |
| GET | `/system/role/list` | 角色列表 | 是 |
| GET | `/system/menu/list` | 菜单列表 | 是 |
| GET | `/system/dept/list` | 部门列表 | 是 |
| GET | `/system/dict/data/list` | 字典数据列表 | 是 |
| GET | `/system/config/list` | 参数列表 | 是 |
| GET | `/monitor/logininfor/list` | 登录日志 | 是 |
| GET | `/monitor/operlog/list` | 操作日志 | 是 |
| GET | `/monitor/server` | 服务器信息 | 是 |

### 2.2 老人管理

| 方法 | 路径 | 说明 | 权限标识 |
| --- | --- | --- | --- |
| GET | `/nursing/elder/list` | 老人列表 | `nursing:elder:list` |
| GET | `/nursing/elder/{id}` | 老人详情 | `nursing:elder:query` |
| POST | `/nursing/elder` | 新增老人 | `nursing:elder:add` |
| PUT | `/nursing/elder` | 修改老人 | `nursing:elder:edit` |
| DELETE | `/nursing/elder/{ids}` | 删除老人 | `nursing:elder:remove` |
| POST | `/nursing/elder/export` | 导出老人 | `nursing:elder:export` |

请求示例：

```http
GET /nursing/elder/list?pageNum=1&pageSize=10&name=张&status=4
Authorization: Bearer <token>
```

响应示例：

```json
{
  "code": 200,
  "msg": "查询成功",
  "rows": [
    {
      "id": 1001,
      "name": "张三",
      "sex": 1,
      "phone": "13800000000",
      "birthday": "1944-05-12",
      "bedId": 20,
      "bedNumber": "A-101-1",
      "status": 4,
      "createTime": "2026-09-20 10:00:00"
    }
  ],
  "total": 1
}
```

### 2.3 机构、房型和床位

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/nursing/floor/list` | 楼层列表 |
| POST | `/nursing/floor` | 新增楼层 |
| PUT | `/nursing/floor` | 修改楼层 |
| DELETE | `/nursing/floor/{ids}` | 删除楼层 |
| GET | `/nursing/room/list` | 房间列表 |
| GET | `/nursing/roomType/list` | 房型列表 |
| GET | `/nursing/bed/list` | 床位列表 |
| PUT | `/nursing/bed` | 修改床位 |

实际路径前缀以 `zzyl-nursing-platform` 中对应 Controller 为准，接口迁移时不得修改路径。

### 2.4 入住与合同

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/nursing/checkIn/detail/{id}` | 入住详情 |
| POST | `/nursing/checkIn/apply` | 入住申请 |
| GET | `/nursing/checkIn/list` | 入住列表 |
| GET | `/nursing/checkIn/{id}` | 入住信息 |
| POST | `/nursing/checkIn` | 新增入住 |
| PUT | `/nursing/checkIn` | 修改入住 |
| DELETE | `/nursing/checkIn/{ids}` | 删除入住 |
| POST | `/nursing/checkIn/export` | 导出入住记录 |
| GET | `/nursing/contract/list` | 合同列表 |
| GET | `/nursing/contract/{id}` | 合同详情 |
| POST | `/nursing/contract` | 新增合同 |
| PUT | `/nursing/contract` | 修改合同 |
| DELETE | `/nursing/contract/{ids}` | 删除合同 |

入住申请示例：

```http
POST /nursing/checkIn/apply
Content-Type: application/json
Authorization: Bearer <token>
```

```json
{
  "checkInElderDto": {
    "name": "张三",
    "idCardNo": "110101194405120011",
    "sex": 1,
    "phone": "13800000000",
    "birthday": "1944-05-12",
    "address": "北京市海淀区"
  },
  "checkInConfigDto": {
    "bedId": 20,
    "nursingLevelId": 3,
    "nursingLevelName": "三级护理",
    "startDate": "2026-10-01 00:00:00",
    "endDate": "2027-09-30 23:59:59",
    "feeStartDate": "2026-10-01 00:00:00"
  },
  "checkInContractDto": {
    "contractName": "养老服务合同",
    "thirdPartyName": "李四",
    "thirdPartyPhone": "13900000000"
  },
  "elderFamilyDtoList": [
    {
      "familyName": "李四",
      "relation": "子女",
      "phone": "13900000000"
    }
  ]
}
```

成功响应：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

业务错误：

| 错误信息 | 触发条件 |
| --- | --- |
| `老人已入住` | 身份证号对应老人已处于入住状态 |
| `床位不存在` | `bedId` 无效 |
| `护理等级不存在` | `nursingLevelId` 无效 |

### 2.5 健康评估

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/nursing/healthAssessment/list` | 评估列表 |
| GET | `/nursing/healthAssessment/{id}` | 评估详情 |
| POST | `/nursing/healthAssessment` | 新增评估 |
| PUT | `/nursing/healthAssessment` | 修改评估 |
| DELETE | `/nursing/healthAssessment/{ids}` | 删除评估 |

健康评分和风险等级编码为待确认项，不得在未确认前自行改变含义。

### 2.6 护理业务

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/nursing/nursingLevel/list` | 护理等级列表 |
| POST | `/nursing/nursingLevel` | 新增护理等级 |
| GET | `/nursing/project/list` | 护理项目列表 |
| POST | `/nursing/project` | 新增护理项目 |
| GET | `/nursing/nursingPlan/list` | 护理计划列表 |
| GET | `/nursing/nursingPlan/{id}` | 护理计划详情 |
| GET | `/nursing/nursingTask/list` | 护理任务列表 |
| GET | `/nursing/nursingTask/{id}` | 护理任务详情 |
| PUT | `/nursing/nursingTask/do` | 执行护理任务 |
| PUT | `/nursing/nursingTask/updateTime` | 改期 |
| PUT | `/nursing/nursingTask/cancel` | 取消任务 |
| POST | `/nursing/nursingTask` | 新增任务 |
| PUT | `/nursing/nursingTask` | 修改任务 |
| DELETE | `/nursing/nursingTask/{ids}` | 删除任务 |

执行护理任务示例：

```http
PUT /nursing/nursingTask/do
Content-Type: application/json
Authorization: Bearer <token>
```

```json
{
  "id": 3001,
  "realServerTime": "2026-10-01 09:20:00",
  "mark": "已完成血压测量，状态正常",
  "taskImage": "/profile/files/master/2026/10/01/task-3001.jpg"
}
```

响应：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 3001,
    "status": 2,
    "realServerTime": "2026-10-01 09:20:00"
  }
}
```

### 2.7 预约管理

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/nursing/reservation/list` | 预约列表 |
| GET | `/nursing/reservation/{id}` | 预约详情 |
| PUT | `/nursing/reservation` | 修改预约 |
| DELETE | `/nursing/reservation/{ids}` | 删除预约 |

### 2.8 AI 助手

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/ai/chat` | 流式对话 |
| GET | `/ai/history` | 获取当前用户对话列表 |
| GET | `/ai/history/{chatId}` | 获取指定对话历史 |
| DELETE | `/ai/history/{chatId}` | 删除指定对话 |

流式对话示例：

```http
POST /ai/chat?prompt=张三最近健康情况如何&chatId=abc123
Authorization: Bearer <token>
```

响应为文本流或 SSE 数据，前端持续追加返回内容。服务端调用 Dify 后解析 `answer` 或 `text` 字段。

### 2.9 Dify 业务查询接口

| 方法 | 路径 | 参数 | 说明 |
| --- | --- | --- | --- |
| GET | `/dify/serve/getElderBasicInfo` | `nameOrId` | 查询老人基本信息 |
| GET | `/dify/serve/getElderHealthInfo` | `nameOrId` | 查询老人健康信息 |
| GET | `/dify/serve/getReservationByToday` | `datetime` 可选 | 查询指定日期预约 |

这些接口应限制来源网络和调用身份，不能作为完全公开接口。

## 3. 家属端小程序接口清单

### 3.1 登录

| 方法 | 路径 | 说明 | 鉴权 |
| --- | --- | --- | --- |
| POST | `/member/user/login` | 原手机号/密码登录，继续保留 | 否 |
| POST | `/member/user/wx-login` | 微信 `code` 换取登录态 | 否 |
| POST | `/member/user/wx-bind` | 首次微信登录绑定原家属账号 | 否 |

请求示例：

```json
{
  "username": "13800000000",
  "password": "待确认"
}
```

如果原系统使用微信授权，应保留原参数结构和登录方式，不新增第二套 Token。

微信登录请求：

```json
{
  "code": "wx-js-code"
}
```

首次登录未绑定时返回：

```json
{
  "code": 202,
  "msg": "bindRequired",
  "data": {
    "bindTicket": "one-time-ticket"
  }
}
```

绑定请求：

```json
{
  "bindTicket": "one-time-ticket",
  "username": "13800000000",
  "password": "原账号密码"
}
```

绑定完成后使用与原登录一致的 Token 结构，不创建第二套登录态。

响应示例：

```json
{
  "code": 200,
  "msg": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "nickName": "李四"
  }
}
```

### 3.2 家属与老人关联

| 方法 | 路径 | 参数 | 说明 |
| --- | --- | --- | --- |
| POST | `/member/user/add` | `MemberElderDto` | 绑定老人 |
| GET | `/member/user/my` | 无 | 我的家人 |
| GET | `/member/user/list-by-page` | 原分页参数 | 分页查询绑定记录 |
| DELETE | `/member/user/deleteById?id=1` | `id` | 解绑 |

绑定示例：

```json
{
  "elderId": 1001,
  "relation": "子女",
  "isPrimary": 1
}
```

我的家人响应示例：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "elderId": 1001,
      "elderName": "张三",
      "sex": 1,
      "age": 82,
      "bedNumber": "A-101-1",
      "status": 4,
      "nursingLevelName": "三级护理"
    }
  ]
}
```

### 3.3 房型与护理项目

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/member/roomTypes?status=1` | 根据状态查询房型 |
| GET | `/member/orders/project/{id}` | 查询护理项目详情 |
| GET | `/member/orders?pageNum=1&pageSize=10&name=护理` | 分页查询护理项目 |

### 3.4 家属预约

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/member/reservation` | 新增预约 |
| GET | `/member/reservation/page?pageNum=1&pageSize=10&status=0` | 分页查询预约 |
| GET | `/member/reservation/countByTime?time=1780000000000` | 查询指定日期时段数量 |
| GET | `/member/reservation/cancelled-count` | 查询当前用户取消次数 |
| PUT | `/member/reservation/{id}/cancel` | 取消预约 |

新增预约示例：

```json
{
  "name": "李四",
  "mobile": "13900000000",
  "time": "2026-10-08 10:00:00",
  "visitor": "张三",
  "type": 1
}
```

预约错误：

| 错误信息 | 触发条件 |
| --- | --- |
| `当前时段已约满` | 同一时段预约数达到 6 条 |
| `当前手机号已预约` | 同一手机号在同一时间已有预约 |
| `当前预约不存在` | 取消的预约 ID 不存在 |

## 4. 鉴权与安全要求

### 4.1 管理端

- 使用 RuoYi-Geek 登录、JWT 和权限注解。
- 写操作校验 `@PreAuthorize` 权限。
- 导出、删除、入住和合同修改记录操作日志。

### 4.2 家属端

- 所有非公开接口检查 Token。
- 查询老人信息前校验 `family_member_elder` 绑定关系。
- 前端传入的 `elderId` 不能作为可信身份依据。
- 401 时清除 Token、昵称和老人缓存，并跳转登录页。

### 4.3 AI 接口

- Dify Token 只保存在后端配置中。
- 查询老人信息时使用已认证用户或受控服务身份。
- 不向模型传递无关敏感字段。
- 记录调用时间、用户、结果状态，不记录完整身份证号等敏感明文。

## 5. 接口兼容性检查

迁移时逐项检查：

| 检查项 | 要求 |
| --- | --- |
| HTTP 方法 | 保持不变 |
| 路径 | 保持不变，大小写敏感路径需重点核对 |
| 请求参数 | 名称和主要类型保持不变 |
| 响应字段 | 原有字段继续返回 |
| 状态编码 | 沿用原编码 |
| 错误信息 | 保留可识别的业务错误文本 |
| 分页结构 | 保持 `rows`、`total` 或原结构 |
| 文件地址 | 旧地址可读或执行迁移 |
| AI 流式格式 | 前端仍能解析 |

## 6. 接口文档工具

RuoYi-Geek 集成 Springdoc 和 Knife4j。开发时可访问：

| 地址 | 说明 |
| --- | --- |
| `/v3/api-docs` | OpenAPI JSON |
| `/doc.html` | Knife4j 页面，路径待确认 |
| `/swagger-ui/index.html` | Springdoc 页面，路径待确认 |

每次新增或修改接口后，必须同步：

1. 本文档接口清单。
2. OpenAPI 注解。
3. 前端 API 文件。
4. 接口回归测试。

## 7. 待确认

| 编号 | 内容 |
| --- | --- |
| TBD-API-01 | 以现有 Controller、前端 API 调用和本文件反推完整接口清单 |
| TBD-API-02 | 原验证码接口准确路径需要从 `3.9.1-G` 启动后的 OpenAPI 页面确认 |
| TBD-API-03 | 原接口中未在代码内出现的账单、订单和合同接口 |
| TBD-API-04 | 文件上传和下载地址在局域网部署后的完整访问前缀 |

已确认：

- 家属端使用微信授权登录，保留原 `/member/user/login`，新增 `/member/user/wx-login` 和 `/member/user/wx-bind`。
- 健康评分和风险等级沿用原数据字典与编码。
- `/dify/serve/*` 只供内网 Dify 工具调用，使用服务 Token 和来源 IP 白名单。
