# 社交电商 App 面试问答（Jetpack Compose）

## 项目概览
- 现代化社交电商 App，包含瀑布流内容展示、即时通讯与购物功能
- 架构：MVVM + 单向数据流（UDF），网络到 UI 全链路响应式
- 技术栈：Kotlin、Jetpack Compose、Coroutines/Flow、Room、Retrofit、OkHttp(WebSocket)、Coil、Android Service

## 常见面试问题与回答

### 为什么选择 Jetpack Compose，而不是传统 View？
- 开发效率更高，声明式 UI 更适合状态驱动的社交/电商场景
- 复杂列表和动画实现更简单，例如瀑布流、吸顶 Tab、分页切换
- 与 Kotlin Flow 天然契合，减少样板代码，状态变更即可驱动 UI 更新

### 瀑布流列表是如何实现和优化的？
- 使用 LazyVerticalStaggeredGrid 展示非等高内容
- 图片加载用 Coil，配置占位图/裁剪，避免滚动白屏
- 仅渲染屏幕内元素，减少不必要重组；按需分页加载，降低首屏压力
- 参考：Home 界面瀑布流与购物页的内容网格

### 单向数据流（UDF）在项目中如何落地？
- ViewModel 暴露 State/Flow，UI 只读订阅并渲染
- 事件从 UI 单向传入 ViewModel 进行处理（如加载、筛选、点赞）
- 数据从 Repository 汇聚 Remote/Local 一致化，再通过 StateFlow 推给 UI
- 优点：数据流向清晰、易于定位问题、便于测试与扩展

### 本地数据与登录态如何管理？
- 结构化数据使用 Room 缓存，实现弱网/离线可用与快速启动
- 轻量设置与登录态使用 DataStore 管理（Token、UserId）
- 登录拦截：未登录时对“消息/我/发布”进行导航拦截，跳转到登录页

### 实时消息与系统通知如何实现直达页面？
- 后端通过 WebSocket 推送 JSON（含 type/targetId）
- 后台 Service 保持 WebSocket 连接，解析消息后触发通知
- 通过 PendingIntent + NavDeepLink，点击通知直达详情或消息页
- 处理返回栈：使用单实例唤起与 onNewIntent，避免主页多实例与栈混乱

### 网络层如何设计，为什么要共享 OkHttpClient？
- Retrofit（REST）与 WebSocket 共享 OkHttpClient，复用连接池与拦截器
- 降低握手与连接开销，统一网络策略（如 Token 拦截、日志）
- 便于统一故障处理与网络监控

### 导航体系如何保证体验与稳定性？
- 使用 Navigation Compose，启用 launchSingleTop 与 popUpTo 保持单实例
- 深链路由用于通知跳转；热启动场景使用 handleDeepLink 与 onNewIntent
- 登录拦截置于底部导航点击逻辑，保证受限页面只能在登录后进入

### 性能与体验优化有哪些？
- 瀑布流列表分页加载，屏内渲染，减少重组
- 图片懒加载与占位，避免白屏；滚动不卡顿
- Snackbar 等轻量反馈，失败可重试，避免打断用户流程

### 如何扩展到评论/关注类通知？
- 按约定的服务端驱动协议（type/targetId），客户端只需新增类型映射与目标页导航
- 复用现有通知、深链和导航机制，无需重构

### 遇到的典型问题与解决思路
- 深链导致导航堆栈异常：统一单实例策略、在 Activity 处理 onNewIntent、谨慎 restoreState
- 未注册路由导致崩溃：在 NavHost 中显式注册所有目标页面（如 Login）
- WebSocket 断连：在 Service 层增加重连与生命周期管理，避免界面销毁带来的断开

### 安全与合规
- Token 等敏感信息仅存储在 DataStore；避免日志打印和明文持久化
- Android 13+ 动态请求通知权限；仅在必要时发送通知

## 快速复述模板
- UI：Compose 声明式构建，瀑布流与吸顶 Tab，图片懒加载，滚动流畅
- 架构：MVVM + Flow 单向数据流，Repository 收敛数据源，状态驱动 UI
- 存储：Room 做结构化缓存，DataStore 管理登录态与偏好
- 网络：Retrofit + WebSocket 共享 OkHttpClient，统一策略，资源复用
- 通知：后端驱动消息 → Service 解析 → PendingIntent + 深链直达页面，返回栈正确
- 导航：单实例导航与登录拦截，稳定可控，避免栈混乱

