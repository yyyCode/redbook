# RedBook 仿小红书 App

本项目使用 Kotlin + Jetpack Compose 构建一个仿小红书的现代化安卓应用，遵循 MVVM 架构与状态驱动的 UI 设计。

## 技术栈
- Kotlin
- Jetpack Compose（Material3）
- Navigation Compose
- Coil（图片加载）
- ViewModel + StateFlow（Kotlin Coroutines）
- Gradle 8.13

## 已实现功能
- 底部导航：Home / Shopping / Message / Me 四大模块
- 首页
  - 纵向滚动
  - 瀑布流布局（LazyVerticalStaggeredGrid）
  - 标签页结构与内容分区
- 购物
  - 顶部搜索栏与更多入口
  - 图标导航与促销卡片
  - 商品瀑布流列表（图片、标题、价格、销量、标签）
- 消息
  - 顶部操作栏（搜索、添加）
  - 快捷入口（赞和收藏、新增关注、评论和@）
  - 空状态与「你可能感兴趣的人」推荐列表
- 我
  - 个人资料（头像、昵称、小红书号）
  - 数据统计（关注、粉丝、获赞与收藏）
  - 快捷卡片（创作灵感、浏览记录）
  - 标签页（笔记、收藏、赞过）与空状态
- 架构与工程
  - MVVM 模式，UI 与数据逻辑解耦
  - 以 StateFlow 驱动 Compose UI 状态
  - Gradle Wrapper 升级至 8.13，兼容最新 AGP

## 待实现功能
- 登录/注册与账号体系
- 与后端 API 对接（列表分页、错误处理、重试机制）
- 笔记详情页、商品详情页
- 发布笔记/图片视频上传与编辑流程
- 搜索输入与结果页（联想、过滤、排序）
- 购物车与订单管理
- 消息实时与通知
- 数据持久化（Room / DataStore）
- 国际化与无障碍适配
- 单元测试与 UI 测试、CI/CD 集成

## 项目亮点
- 纯 Compose 架构，简洁高效，状态单向流动
- 瀑布流布局与现代电商页面还原度高
- MVVM + StateFlow，易扩展、易测试、可维护
- 模块边界清晰，屏幕与 ViewModel 解耦
- 统一的暗色视觉与一致的交互细节

## 项目截图（占位）
请将实际截图替换下方占位路径并提交到仓库中。

### 首页
![首页](/screenshots/1.png)

### 购物
![购物](screenshots/2.png)

### 消息
![消息](screenshots/3.png)

### 我
![我](screenshots/4.png)

