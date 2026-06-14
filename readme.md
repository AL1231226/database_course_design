# 体育用品批发销售信息系统

## 项目概述

本项目是一个基于 Web 的体育用品批发销售信息管理系统，旨在为体育用品批发商提供一套完整的进销存管理解决方案。系统实现了商品库存管理、客户管理、生产厂家管理、订单处理等核心业务功能，同时为零售客户提供商品浏览与在线下单的能力。

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| **前端** | Vue 3 + TypeScript | Vue 3.5 / TS 6.0 |
| 前端构建 | Vite | 8.x |
| 前端路由 | Vue Router | 5.x |
| HTTP 客户端 | Axios | 1.x |
| **后端** | Spring Boot | 4.0.6 |
| ORM 框架 | MyBatis Spring Boot Starter | 4.0.1 |
| 数据库 | MySQL | — |
| 构建工具 | Maven | — |
| 语言 | Java | 25 |
| 认证方案 | JWT (jjwt) + BCryptPasswordEncoder | — |
| 工具库 | Lombok | — |

## 项目结构

```
数据库课设/
├── 前端代码/sale_fronted/       # Vue 3 前端项目
│   ├── src/
│   │   ├── admin/               # 管理员端页面（6个）
│   │   ├── customer/            # 客户端页面（5个）
│   │   ├── api/                 # API 接口封装
│   │   ├── router/              # 路由配置 + 路由守卫
│   │   ├── assets/              # 静态资源
│   │   ├── Login.vue            # 登录/注册页面
│   │   ├── App.vue              # 根组件
│   │   └── main.ts              # 入口文件
│   ├── package.json
│   └── vite.config.ts
├── 后端服务代码/Sale_Information_System/   # Spring Boot 后端项目
│   ├── src/main/java/com/example/Sale_Information_System/
│   │   ├── Controller/          # 控制器层（6个）
│   │   ├── Service/             # 业务逻辑层
│   │   ├── Mapper/              # MyBatis 数据访问层（6个）
│   │   ├── pojo/                # 实体类（8个）
│   │   ├── config/              # 配置类（CORS + 拦截器）
│   │   └── util/                # 工具类（JwtUtil + UserContext）
│   ├── src/main/resources/mapper/  # MyBatis XML 映射（6个）
│   ├── pom.xml
│   └── ...
├── 数据库/
│   └── init.sql                 # 数据库初始化脚本
├── log/                          # 开发日志
└── 项目介绍.md                   # 本文档
```

## 数据库设计

系统采用 MySQL 数据库，共包含 6 张表：

| 表名 | 说明 | 主要字段 |
|------|------|----------|
| **admin** | 管理员 | 编号、用户名、密码(BCrypt)、姓名 |
| **customer** | 顾客信息 | 顾客代码、名称、联系人、地址、邮编、电话、密码(BCrypt) |
| **manufacturer** | 生产厂家信息 | 厂家代码、名称、联系人、联系电话、地址 |
| **product** | 库存商品 | 商品编号、分类编码、厂家编码、说明、单价、库存数量 |
| **orders** | 订单信息 | 订单号、下单日期、顾客代码、运输要求、供货状态、运输日期、重量、运费、付款信息、付款状态、是否取消、管理员反馈 |
| **order_detail** | 订单明细 | 细则编号、订单号、商品编号、厂家名称(快照)、数量、单价、总金额 |

### 关键业务规则

- **运费计算：** 订购商品 5 件以内免费，超出部分每件加 3 元
- **订单取消：** 使用软删除 `is_cancelled` 字段，不实际删除记录
- **订单状态：** unpaid（未付款）→ available（已供货）→ paid（已完成），或 unpaid → cancelled（已取消）
- **ID 自动生成：** 顾客使用 `CUST%03d`、厂家使用 `MFR%03d`、商品使用 `P%03d` 格式递增

## 功能模块

### 认证系统

| 功能 | 说明 |
|------|------|
| **登录** | 支持顾客和管理员双角色登录，顾客可用顾客代码或手机号登录 |
| **注册** | 新顾客自主注册，填写名称、联系人、电话、地址、邮编、密码 |
| **修改密码** | 登录顾客可自主修改密码（管理员无此按钮） |
| **路由守卫** | 未登录自动跳转登录页，按角色限制页面访问 |
| **JWT 鉴权** | 后端拦截器校验 Token，放行登录注册和商品浏览接口 |

### 管理员端

| 功能 | 说明 |
|------|------|
| **仪表盘**（Dashboard） | 系统概览与数据统计 |
| **客户管理**（CustomerManage） | 管理顾客信息，增删改查；新增顾客默认密码 123456 |
| **生产厂家管理**（ManufacturerManage） | 管理生产厂家信息，增删改查 |
| **库存管理**（InventoryManage） | 管理商品库存信息，增删改查 + 低库存预警 |
| **订单管理**（OrderManage） | 查看和处理订单，按状态筛选，确认供货（自动扣库存）、标记付款、取消订单、填写发货反馈 |

### 客户端

| 功能 | 说明 |
|------|------|
| **商品列表**（ProductList） | 浏览商品目录，查看商品详情 |
| **分类商品**（CategoryProducts） | 按分类筛选商品 |
| **购物车**（ShoppingCart） | 管理购物车，提交订单（自动使用当前登录用户，无需手动选顾客） |
| **订单历史**（OrderHistory） | 查看历史订单及状态，按全部/未付款/未发货/已完成/已取消筛选，查看管理员发货反馈 |

## 系统架构

系统采用前后端分离架构，基于 JWT 认证方案实现用户身份管理：

1. **前端**（Vue 3 + TypeScript）
   - Axios 调用后端 REST API，自动携带 JWT Token
   - Vue Router 管理页面路由，路由守卫按角色控制访问权限
   - 登录信息存储在 sessionStorage，axios 响应拦截器处理 401 自动跳转登录

2. **后端**（Spring Boot + MyBatis）
   - RESTful API 分层架构：Controller → Service → Mapper → XML
   - AuthInterceptor 拦截 `/api/**` 请求（放行 `/api/auth/**` 和 `/api/products/**`）校验 JWT
   - UserContext ThreadLocal 存储当前登录用户信息

3. **数据库**（MySQL）
   - 6 张表：admin、customer、manufacturer、product、orders、order_detail
   - 外键关联保证数据参照完整性

## API 接口概览

| 控制器 | 接口前缀 | 说明 |
|--------|----------|------|
| AuthController | `/api/auth` | 登录、注册、修改密码 |
| ProductController | `/api/products` | 商品 CRUD、分类查询、低库存查询 |
| CustomerController | `/api/customers` | 顾客 CRUD |
| OrderController | `/api/orders` | 订单 CRUD、状态管理、我的订单、发货反馈 |
| ManufacturerController | `/api/manufacturers` | 厂家 CRUD |

## 开发与运行

### 前置要求

- Node.js >= 22.12
- Java 25
- Maven
- MySQL 数据库

### 本地开发

1. 使用 `init.sql` 初始化数据库
2. 执行 `ALTER TABLE customer ADD COLUMN password VARCHAR(100)` 添加密码字段
3. 创建 admin 表（admin_id, username, password, admin_name）并插入默认管理员
4. 启动后端 Spring Boot 项目
5. 进入前端目录运行 `npm install` 安装依赖，`npm run dev` 启动开发服务器
6. 访问 `http://localhost:5173/login`，顾客用 `CUST001/123456` 或管理员用 `admin/123456` 登录

---

