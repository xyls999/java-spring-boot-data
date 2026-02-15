# 微头条全栈项目（Spring Boot + Vue3）

本仓库当前实现功能：
- 用户注册 / 登录 / JWT 鉴权
- 新闻分页浏览、按标题关键字搜索、详情查看
- 新闻新增、编辑、删除（仅作者可操作）
- 热度体系：新闻详情访问自动累计热度
- 排行榜：历史总排、日排行、周排行
- 社交能力：关注、取关、关注列表、好友（互相关注）列表
- 前端 UI 使用 Element-Plus 做了布局与交互优化

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

鉴权请求头：
```text
Authorization: Bearer <token>
```
