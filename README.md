# 微头条全栈项目（Spring Boot + Vue3）

本仓库当前实现功能：
- 用户注册 / 登录 / JWT 鉴权
- 新闻分页浏览、按标题关键字搜索、详情查看
- 新闻新增、编辑、删除（仅作者可操作）
- 热度体系：新闻详情访问自动累计热度
- 排行榜：历史总排、日排行、周排行
- 社交能力：关注、取关、关注列表、好友（互相关注）列表
- 前端 UI 使用 Element-Plus 做了布局与交互优化

## 新增优化（多线程 + 分布式）
- **多线程异步化**：新闻详情访问时，热度累加与浏览日志写入通过 `@Async` 线程池异步执行，降低请求阻塞。
- **线程池可配置**：`app.thread.heat.*` 支持核心线程、最大线程、队列长度配置。
- **分布式排行榜刷新锁**：使用 MySQL `GET_LOCK/RELEASE_LOCK`，在多实例部署时仅允许一个节点执行定时刷新，避免重复刷新风暴。
- **节点识别接口**：`GET /api/system/node` 用于识别当前服务节点，便于多节点排障。

## 目录结构
- `backend/`：Spring Boot 3 + MyBatis-Plus + Druid + MySQL8
- `frontend/`：Vite + Vue3 + Pinia + Router + Axios + Element-Plus

## 后端启动
1. 创建数据库并执行：`backend/src/main/resources/schema.sql`
2. 修改 `backend/src/main/resources/application.yml` 的数据库账号密码
3. 启动：
```bash
cd backend
mvn spring-boot:run
```

## 前端启动
```bash
cd frontend
npm install
npm run dev
```
默认前端端口 `5173`，Vite 代理 `/api` 到 `http://localhost:8080`。

## API 摘要
- `POST /api/auth/register`：注册
- `POST /api/auth/login`：登录
- `GET /api/news/list`：新闻分页 + 搜索
- `GET /api/news/{id}`：新闻详情（会增加热度）
- `GET /api/news/rank?period=total|day|week`：排行榜
- `POST /api/news/manage`：新增新闻（需 token）
- `PUT /api/news/manage/{id}`：修改新闻（需 token）
- `DELETE /api/news/manage/{id}`：删除新闻（需 token）
- `POST /api/social/follow/{targetId}`：关注
- `DELETE /api/social/follow/{targetId}`：取关
- `GET /api/social/following`：我的关注
- `GET /api/social/friends`：我的好友
- `GET /api/system/node`：节点信息（分布式排障）

鉴权请求头：
```text
Authorization: Bearer <token>
```
