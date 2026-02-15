# 微头条全栈项目（Spring Boot + Vue3）

本仓库实现了你要求的全部核心功能：
- 用户注册 / 登录
- JWT 鉴权
- 新闻分页浏览
- 按标题关键字搜索
- 新闻详情
- 新闻新增、编辑、删除（仅作者可操作）
- 前端 UI 基于 Element-Plus 做了布局和视觉优化

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

默认前端端口 `5173`，通过 Vite 代理转发 `/api` 到 `http://localhost:8080`。

## API 摘要

- `POST /api/auth/register`：注册
- `POST /api/auth/login`：登录
- `GET /api/news/list`：分页 + 搜索
- `GET /api/news/{id}`：详情
- `POST /api/news/manage`：新增（需 token）
- `PUT /api/news/manage/{id}`：修改（需 token）
- `DELETE /api/news/manage/{id}`：删除（需 token）

鉴权请求头：

```text
Authorization: Bearer <token>
```
