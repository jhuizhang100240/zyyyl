<!-- generated-by: project-guardrails | 2026-09-30 | 继承根目录 AGENTS.md -->

# 智颐云养老管理端开发约束

本文档继承根目录 `AGENTS.md` 的全部约束，只补充管理端专属规则。

执行全局重命名前，当前物理目录仍为 `ruoyi-zyyyl-vue3`；R-06 完成后使用 `ruoyi-zyyyl-vue3`。

## 一、目录结构

```text
ruoyi-zyyyl-vue3/
├─ src/
│  ├─ api/
│  ├─ assets/
│  ├─ core/
│  ├─ routes/
│  ├─ views/
│  ├─ App.vue
│  └─ main.ts
├─ packages/
│  ├─ core/
│  └─ ui/
├─ modules/
│  └─ nursing/
└─ vite/
```

| 目录 | 放什么 | 禁止事项 |
| --- | --- | --- |
| `src/` | 应用壳、全局入口、公共路由 | 堆叠养老业务页面 |
| `packages/core/` | 请求、路由、缓存、认证基础设施 | 修改护理业务 |
| `packages/ui/` | 通用组件、布局、设计令牌 | 写入具体护理页面 |
| `modules/nursing/src/api/` | 老人、入住、护理、预约接口 | 在页面中散写 URL |
| `modules/nursing/src/views/` | 护理业务页面 | 复制请求和权限守卫 |
| `modules/nursing/src/components/` | 护理业务复用组件 | 放通用按钮和表格基础封装 |

养老业务模块路径统一使用 `modules/nursing/`。禁止把新页面全部塞进 `src/views`。

## 二、技术基线

| 技术 | 版本或规则 |
| --- | --- |
| Vue | `3.5.34` |
| Vite | `8.0.12` |
| Element Plus | `2.14.0` |
| Pinia | `3.0.4` |
| Vue Router | `5.0.6` |
| Axios | `1.16.0` |
| Node.js | `^20.19.0` 或 `>=22.12.0` |
| 包管理器 | `pnpm` |
| 开发端口 | `5173` |
| 生产端口 | Nginx `80` |
| 后端代理目标 | `http://localhost:8080` |

新业务模块使用 TypeScript。已有 JavaScript 页面允许渐进迁移，但不得在同一文件混用两套风格。

## 三、设计来源与流程

页面开发前必须读取：

- `docs/02-UI页面原型设计方案.md`
- `docs/06-项目整体修改文档.md` 的品牌修改清单
- `plan/管理端-plan.md` 的“设计稿链接收集表”

页面未在设计稿或原型文档中定义时，不得自由发挥。需要新增页面时，先将页面加入 `plan/管理端-plan.md` 的链接收集表，并取得用户确认。

页面状态必须覆盖：

- 加载态
- 空状态
- 错误态
- 无权限态
- 表单校验态
- 提交中禁止重复点击

违反示例：老人列表请求失败后只显示白屏，没有重试入口。

## 四、请求与数据流

- 所有 HTTP 请求复用 `packages/core/src/utils/request.ts` 的 `request`、`postAction`、`getAction`。
- 页面和组件禁止直接调用 `axios` 或 `fetch`。
- 接口路径集中放在 `modules/nursing/src/api/*.ts` 或 `src/api/*.ts`。
- 后端地址来自 `VITE_APP_BASE_API`，禁止在页面中写 `http://localhost:8080`。
- 401、重复提交和通用错误由请求层统一处理，页面只处理自身业务分支。
- 跨页面共享数据放 Pinia；弹窗开关、临时表单等局部状态留在组件。

违反示例：

```javascript
axios.get('http://localhost:8080/nursing/elder/list')
```

## 五、路由与权限

- 动态菜单来自后端 `getRouters`，不得在前端硬编码完整权限菜单。
- 路由和菜单路径保持原系统兼容。
- 页面按钮权限使用现有权限指令，权限串保持 `nursing:<资源>:<动作>`。
- 前端权限只负责展示，后端接口仍然是最终权限边界。

老人列表按钮示例：

```vue
<el-button v-hasPermi="['nursing:elder:add']">新增老人</el-button>
```

禁止只在按钮上隐藏删除操作，却允许删除接口裸调用。

## 六、样式与品牌

- 品牌名称统一为“智颐云养老”。
- 主色、背景、文字、状态色使用 `packages/ui/src/styles/tokens.scss` 中的 CSS 变量。
- 不新增第二套主题变量，不在页面中散写品牌色十六进制值。
- Element Plus 主题覆盖集中放在 `src/assets/styles/element-theme.scss`。
- 组件样式默认使用 `scoped`，禁止用全局 `.title`、`.card` 污染全站。

禁止：

- 继续显示 RuoYi、旧框架品牌、中州养老等旧品牌。
- 使用紫色 AI 渐变作为主视觉。
- 用 Emoji 作为菜单或按钮图标。

## 七、页面与组件规范

- 页面标题、筛选区、操作区、表格和分页结构保持一致。
- 表格超过 50 行时使用分页或虚拟滚动。
- 图片声明宽高或 `aspect-ratio`。
- 状态标签同时使用文字、图标和颜色，不能只用颜色。
- 危险操作必须二次确认。
- 一个页面只保留一个主要按钮。

老人列表页面必须包含：

- 姓名、身份证号、床位状态和护理等级筛选。
- 新增、导出、详情、编辑操作。
- 手机号和身份证号脱敏展示。
- 分页、排序、空状态和请求失败重试。

## 八、无障碍与响应式

- 正文默认不小于 16px。
- 触控目标不小于 44 x 44px。
- 键盘焦点清晰，固定侧栏和弹窗不得遮挡焦点。
- 正常文本对比度不低于 4.5:1。
- 表单使用可见标签，错误显示在字段下方。
- 375px、768px、1024px、1440px 均为验收宽度。

## 九、构建与自测

```powershell
pnpm install
pnpm dev
pnpm build:prod
```

页面任务完成的最低证据：

- `pnpm build:prod` 成功。
- 页面在 1440px 和 375px 宽度检查无横向溢出。
- 网络请求路径与 `docs/05-API接口文档.md` 一致。
- 加载、空、错误和权限状态均可触发验证。

## 十、会话恢复流程

1. 读根目录 `AGENTS.md`。
2. 读本文件。
3. 读 `plan/总体协调计划.md`、`plan/管理端-plan.md`、`plan/待确认项.md`。
4. 报告当前页面、已完成任务卡、下一任务和设计稿缺口。
5. 等用户确认后继续。
