<!-- generated-by: project-guardrails | 2026-09-30 | 最终目录名 zyyyl-family-uniapp | 继承根目录 AGENTS.md -->

# 智颐云养老家属端小程序开发约束

本文档继承根目录 `AGENTS.md` 的全部约束，只补充家属端小程序专属规则。

## 一、工作区约定

当前目录是家属端 uni-app 源码工作区。根目录 `zzyl-app/` 是已编译小程序参考产物，只读。

全局重命名完成后，本工作区目录名改为 `zyyyl-family-uniapp`。

禁止直接编辑：

- `zzyl-app/common/vendor.js`
- `zzyl-app/app.js`
- `zzyl-app/app.json`
- `zzyl-app/pages/**/*.js`

需要恢复页面时，先对照参考产物，再在本目录创建 Vue 3 + uni-app 源码。

## 二、技术基线

| 项目 | 规则 |
| --- | --- |
| 框架 | uni-app + Vue 3 |
| 构建工具 | HBuilderX 或 uni-app CLI，实施前确认 |
| 目标平台 | 微信小程序 |
| 状态管理 | Pinia 或轻量 Store，只在跨页面状态使用 |
| HTTP | 统一 `request` 封装 |
| 样式 | SCSS + 设计令牌 |

AppId 和 AppSecret 不得提交仓库。AppSecret 只放后端环境变量。

## 三、页面结构

主包页面：

- `pages/index/index`：首页
- `pages/family/index`：家人
- `pages/family/binding`：绑定家人
- `pages/service/index`：护理服务
- `pages/service/details`：服务详情
- `pages/service/orderVerify`：订单核销或确认
- `pages/login/index`：登录
- `pages/my/index`：个人中心

分包页面：

- `subPages/healthy/index`：健康信息
- `subPages/appointment/**`：预约
- `subPages/bill/index`：账单
- `subPages/contract/index`：合同
- `subPages/order/**`：订单
- `subPages/pay/index`：支付
- `subPages/search/index`：搜索

底部导航只保留首页、家人、服务、我的四个入口，不超过 5 个。

## 四、请求与鉴权

所有请求经过 `utils/request.js` 或等价的 TypeScript 封装：

- 从本地存储读取 Token。
- 请求头使用 `authorization`，值为 Token。
- 同时兼容服务端要求的 `Bearer <token>` 格式，最终以联调为准。
- 401 时清除 Token、昵称和老人缓存，再跳转 `/pages/login/index`。
- 业务错误显示可理解的信息，不直接展示后端堆栈。

基础地址必须来自环境配置：

```javascript
const baseUrl = import.meta.env.VITE_API_BASE_URL
```

禁止硬编码：

```javascript
const baseUrl = 'http://192.168.100.168:8080/member'
```

开发环境基础地址为 `http://192.168.100.168:8080/member`，由环境变量覆盖。小程序只在局域网联调，不配置公网域名和正式 HTTPS。

原接口路径保持不变：

- `/user/login`
- `/user/wx-login`
- `/user/wx-bind`
- `/user/my`
- `/user/add`
- `/user/list-by-page`
- `/user/deleteById`
- `/reservation`
- `/reservation/page`
- `/reservation/countByTime`
- `/orders/project/{id}`
- `/roomTypes`

## 五、家属数据权限

- 绑定老人后，页面才能展示老人健康、护理、账单和合同数据。
- 前端不得把 `elderId` 当作授权凭据。
- 解绑后清除老人相关缓存。
- 同一家属与同一老人不得重复绑定。

违反示例：直接修改本地缓存的 `elderId` 后请求健康详情，且服务端未校验绑定关系。

## 六、品牌与视觉

- 应用名称统一为“智颐云养老”。
- `manifest.json`、`pages.json`、分享标题、首页、登录页统一品牌。
- 主色使用全局设计令牌，不在页面中散写品牌色。
- 长者相关内容正文不小于 17px，触控目标不小于 44 x 44px。
- 固定顶部和底部栏必须处理安全区。
- 状态不能只使用颜色表达。

禁止：

- 显示旧品牌或机构占位名称。
- 使用 Emoji 作为功能图标。
- 使用横向滚动作为主交互。
- 显示虚假支付成功状态；首期不接真实微信支付。

## 七、页面状态与交互

每个异步页面必须有：

- 加载态
- 空状态
- 失败态和重试
- 登录失效态
- 提交中状态

预约页面必须：

- 显示时段剩余名额。
- 校验手机号格式。
- 提示同一手机号同一时间只能预约一次。
- 提交成功后展示预约状态。

## 八、微信小程序发布约束

- 请求域名必须在微信公众平台配置为合法域名。
- 生产环境必须使用 HTTPS。
- `subPages/pay/index` 只做接口预留和状态展示，不接入真实支付。
- 隐私协议和用户协议必须使用正式文本。
- 体验版需在 iOS 和 Android 各检查一次。

## 九、构建与自测

构建命令以最终工程脚手架为准。创建工程后必须回填：

```bash
npm install
npm run dev:mp-weixin
npm run build:mp-weixin
```

页面任务完成的最低证据：

- 微信开发者工具可编译并打开页面。
- 登录、绑定和至少一个业务列表接口请求成功。
- 375px 左右宽度无横向溢出。
- 401 能回到登录页。
- 底部导航和安全区显示正常。

## 十、会话恢复流程

1. 读根目录 `AGENTS.md`。
2. 读本文件。
3. 读 `plan/总体协调计划.md`、`plan/家属端-plan.md`、`plan/待确认项.md`。
4. 报告当前页面、已完成任务卡、下一任务和源码缺口。
5. 等用户确认后继续。
