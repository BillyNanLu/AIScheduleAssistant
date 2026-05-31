# **AI Schedule Assistant**

AI Schedule Assistant（智能日程规划助手）是一款基于 Vue3 与 Spring Boot 构建的智能日程管理平台，通过自然语言解析、语音输入和智能提醒等能力，帮助用户更加高效地完成日程规划与时间管理。

## **一、项目亮点**

- 🎤 语音输入创建日程
- 🧠 中文自然语言时间解析
- 📅 FullCalendar 可视化日历管理
- 🔔 浏览器实时通知提醒
- 📨 消息中心与通知持久化
- 🔐 JWT 用户认证与权限控制
- ⚡ Vue3 + Spring Boot 前后端分离架构

---

## **二、功能模块**

### **1、用户管理**

- 用户注册与登录
- JWT 身份认证
- 用户信息管理
- 头像管理

### **2、日程管理**

- 创建、编辑、删除日程
- 日历视图展示
- 日程状态管理
- 多维度查看日程信息

### **3、智能录入**

支持自然语言输入：

```tex
明天下午三点开项目会议
后天上午九点参加答辩
下周一提交项目报告
```

系统自动解析时间信息并生成对应日程。

### **4、语音交互**

基于 Web Speech API 实现语音识别，用户可通过语音快速创建日程，提高录入效率。

### **5、智能提醒**

系统自动检测即将开始的日程：

- 浏览器通知提醒
- 消息中心提醒
- 已读/未读状态管理

---

## **三、 技术栈**

| **模块** | **技术**                                                     |
| -------- | ------------------------------------------------------------ |
| 后端     | Spring Boot、MyBatis、MySQL、JWT、Redis                      |
| 前端     | Vue3、Vite、Vue Router、Pinia、Element Plus、Axios、FullCalendar、chrono-node |
| 开发工具 | IntelliJ IDEA / VSCode                                       |

---

## **四、项目架构**

### **1、前端（Frontend）**

```tex
frontend
├── src
│   ├── api
│   ├── components
│   ├── views
│   ├── stores
│   ├── router
│   ├── utils
│   └── assets
```

### **2、后端（Backend）**

```tex
backend
├── controller
├── service
├── mapper
├── pojo
├── config
├── interceptor
└── utils
```

---

## **五、数据库设计**

### 1、User（用户信息表）

| 字段名      | 类型         | 说明                         |
| ----------- | ------------ | ---------------------------- |
| id          | BIGINT       | 主键ID                       |
| username    | VARCHAR(50)  | 用户名（唯一）               |
| password    | VARCHAR(64)  | 密码（加密存储）             |
| avatar      | VARCHAR(255) | 用户头像URL                  |
| nickname    | VARCHAR(50)  | 用户昵称                     |
| email       | VARCHAR(100) | 邮箱                         |
| phone       | VARCHAR(20)  | 手机号                       |
| role        | TINYINT      | 角色（1=普通用户，0=管理员） |
| create_time | DATETIME     | 注册时间                     |
| update_time | DATETIME     | 更新时间                     |
| last_login  | DATETIME     | 最近登录时间                 |

---

### 2、Schedule（日程表）

| 字段名        | 类型        | 说明                     |
| ------------- | ----------- | ------------------------ |
| id            | BIGINT      | 主键ID                   |
| user_id       | BIGINT      | 所属用户ID               |
| title         | VARCHAR(50) | 日程标题                 |
| description   | TEXT        | 日程描述                 |
| start_time    | DATETIME    | 开始时间                 |
| end_time      | DATETIME    | 结束时间                 |
| status        | TINYINT     | 状态（0=待办，1=已完成） |
| reminder_time | DATETIME    | 提醒时间                 |
| create_time   | DATETIME    | 创建时间                 |
| update_time   | DATETIME    | 更新时间                 |

---

### 3、Notification（通知表）

| 字段名      | 类型         | 说明                       |
| ----------- | ------------ | -------------------------- |
| id          | BIGINT       | 通知ID                     |
| user_id     | BIGINT       | 用户ID                     |
| schedule_id | BIGINT       | 关联日程ID                 |
| title       | VARCHAR(200) | 通知标题                   |
| content     | VARCHAR(500) | 通知内容                   |
| type        | VARCHAR(50)  | 通知类型（默认 REMINDER）  |
| is_read     | TINYINT      | 是否已读（0=未读，1=已读） |
| create_time | DATETIME     | 创建时间                   |

---

### 表关系说明

| 表名                    | 关系                             |
| ----------------------- | -------------------------------- |
| user → schedule         | 一对多，一个用户可以拥有多个日程 |
| user → notification     | 一对多，一个用户可以拥有多个通知 |
| schedule → notification | 一对多，一个日程可以关联多个通知 |

---

## **六、核心流程**

### **1、日程创建**

```tex
语音输入
    ↓
语音识别
    ↓
时间解析
    ↓
生成日程
    ↓
日历展示
    ↓
通知提醒
```

### **2、日程提醒**

```tex
查询日程
    ↓
检测即将开始事件
    ↓
生成通知
    ↓
保存 notification
    ↓
浏览器通知
    ↓
消息中心展示
```

---

## **七、项目亮点**

- 支持中文自然语言时间解析
- 支持语音创建日程
- FullCalendar 可视化日历
- 浏览器实时通知提醒
- 消息中心与通知持久化
- Vue3 + SpringBoot 前后端分离架构
- JWT 用户认证机制

---

## **八、后续规划**

- AI 智能规划建议
- 大模型辅助生成周计划
- 邮件提醒功能
- 日程分类与标签管理
- 数据统计分析

---

## 九、项目演示视频

百度云网盘：
链接: https://pan.baidu.com/s/1t1B4BhkVdd0vI-AGBBtVWw?pwd=8989 

提取码: 8989

---

## 十、运行说明

### 环境要求

#### 前端环境

- Node.js 18+
- npm 9+

#### 后端环境

- JDK 17+
- Maven 3.8+
- MySQL 8.0+

---

### 1. 克隆项目

```bash
git clone https://github.com/BillyNanLu/AI-Schedule-Assistant.git  cd AI-Schedule-Assistant 
```

---

### 2. 创建数据库

在 MySQL 中创建数据库：

```sql
CREATE DATABASE ai_schedule_assistant DEFAULT CHARACTER SET utf8mb4; 
```

导入项目提供的 SQL 文件：

```sql
source ai_schedule_assistant.sql; 
```

或直接在数据库工具中执行 SQL 脚本。

---

### 3. 配置后端数据库连接

修改：

```tex
backend/src/main/resources/application.yml 
```

数据库配置：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ai_schedule_assistant?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
```

根据实际数据库账号和密码进行修改。

---

### 4. 启动后端项目

进入后端目录：

```bash
 cd backend 
```

使用 Maven 启动：

```bash
mvn spring-boot:run 
```

或直接通过 IDEA 运行：

```tex
 AiScheduleAssistantApplication.java 
```

启动成功后：

```tex
 http://localhost:8080 
```

---

### 5. 启动前端项目

进入前端目录：

```bash
cd frontend 
```

安装依赖：

```bash
npm install 
```

启动项目：

```bash
npm run dev 
```

启动成功后访问：

```tex
http://localhost:5173 
```

---

### 6. 浏览器权限设置

#### 语音识别

首次使用语音输入时：

浏览器会请求麦克风权限。

请点击：

```tex
允许（Allow） 
```

---

#### 浏览器通知

首次进入系统时：

浏览器会请求通知权限。

请点击：

```tex
允许（Allow）
```

否则无法接收日程提醒通知。

---

### 7、默认运行流程

```tex
注册账号
    ↓
登录系统
    ↓
创建日程
    ↓
日历展示
    ↓
消息提醒
    ↓
浏览器通知
```

---

### 8、常见问题

#### 语音输入无法使用

检查：

- 浏览器是否支持 Web Speech API
- 是否授予麦克风权限
- 是否使用 HTTPS 或 localhost 访问

推荐浏览器：

- Google Chrome
- Microsoft Edge

---

#### 浏览器通知未弹出

检查：

- 是否授予通知权限
- 浏览器是否开启消息通知
- 系统是否允许浏览器发送通知

---

#### 后端无法连接数据库

检查：

```yaml
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

配置是否正确。

---

### 9、项目访问地址

| 服务     | 地址                  |
| -------- | --------------------- |
| Frontend | http://localhost:5173 |
| Backend  | http://localhost:8080 |

---

### 测试账号

用户名：alice 
密码：alice123 

如数据库中不存在，请自行注册账号。

---



## **License**

- **作者**：Nan Lu
- **角色**：项目经理 / 全栈工程师
- **邮箱**：[lunan96789@gmail.com](mailto:lunan96789@gmail.com)
- **GitHub**：[github.com/BillyNanLu](https://github.com/BillyNanLu)