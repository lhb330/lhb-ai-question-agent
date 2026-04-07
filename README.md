# lhb-ai-question-agent

一个基于 Spring Boot 3 + Maven 多模块架构构建的 AI 题目生成与管理后端项目。

项目围绕题目生成、题目校验、向量检索、知识点管理、用户权限与基础数据管理等能力展开，适合作为 AI 教育题库类系统的后端基础工程。

## 项目特点

- 基于 Maven 多模块拆分，职责清晰，便于维护
- 使用 Spring Boot 3 构建 Web 服务
- 使用 MyBatis-Plus 进行数据访问
- 使用 PostgreSQL 作为核心数据库
- 集成 Redis，用于缓存和登录态等场景
- 集成 Sa-Token，支持认证鉴权
- 集成 Spring AI Alibaba / DashScope，支持 AI 能力接入
- 支持向量检索相关能力，适用于题目去重、语义匹配、知识库检索等场景

## 技术栈

- Java 21
- Maven
- Spring Boot 3.5.11
- MyBatis-Plus
- PostgreSQL
- Redis
- Sa-Token
- Spring AI Alibaba
- DashScope
- pgvector
- Knife4j / OpenAPI

## 项目结构

```text
lhb-ai-question-agent/
├─ .mvn/                           Maven Wrapper 相关文件
├─ question-common/                公共模块
├─ question-dao/                   数据访问层
├─ question-entity/                实体、DTO、VO 模块
├─ question-service/               业务服务层
├─ question-web/                   Web 启动与接口层
├─ .gitignore                      Git 忽略规则
├─ pom.xml                         父工程 POM
├─ PROJECT_STRUCTURE.md            项目目录架构说明文档
└─ question-sql.sql                数据库脚本
```

### 模块职责

#### `question-common`

存放公共基础能力，例如：

- 常量定义
- 枚举定义
- 全局异常
- 通用工具类

#### `question-dao`

数据访问层，主要包含：

- MyBatis Mapper 接口
- Mapper XML 映射文件

#### `question-entity`

统一管理系统数据模型，主要包含：

- `entity`：数据库实体
- `dto`：请求或传输对象
- `vo`：视图返回对象
- `typeHandlers`：MyBatis 自定义类型处理器

#### `question-service`

核心业务逻辑层，主要包含：

- 服务接口与实现
- AI Prompt 定义
- 业务工具类

#### `question-web`

项目启动与对外接口层，主要包含：

- Spring Boot 启动类
- `controller` 接口控制器
- `config` 配置类
- `auth` 权限校验
- `tools` AI 辅助工具能力

## 依赖关系

可以将项目按下面方式理解：

```text
question-web
    ↓
question-service
    ↓
question-dao
    ↓
question-entity

question-common
    └─ 为多个模块提供公共支持
```

## 环境要求

在本地运行前，建议准备以下环境：

- JDK 21
- Maven 3.9+
- PostgreSQL 18.3
- Redis

## 配置说明

项目运行配置位于：

- 本地配置文件：`question-web/src/main/resources/application.yaml`
- 示例配置文件：`question-web/src/main/resources/application.example.yaml`

建议使用方式：

1. 参考 `application.example.yaml`
2. 在本地创建或修改 `application.yaml`
3. 填入你自己的数据库、Redis、JWT、DashScope 等配置

注意：

- `application.yaml` 中通常包含敏感信息，不建议提交到公开仓库
- 当前仓库已经将该文件加入 `.gitignore`

## 本地启动

### 1. 安装依赖并编译

在项目根目录执行：

```bash
mvn clean install
```

### 2. 配置数据库和缓存

确保以下服务可用：

- PostgreSQL
- Redis

并根据实际环境修改：

- 数据库连接地址
- 数据库用户名和密码
- Redis 地址
- DashScope API Key
- JWT 密钥

### 3. 启动项目

可以在项目根目录执行：

```bash
mvn -pl question-web spring-boot:run
```

或者先打包后运行：

```bash
mvn clean package
java -jar question-web/target/question-web.jar
```

## 接口文档

项目已集成 OpenAPI / Knife4j，启动后可访问：

- Swagger UI：`http://localhost:9123/api/swagger-ui.html`

如果配置未改动，服务默认端口为：

- `9123`

上下文路径为：

- `/api`

## 数据库脚本

根目录下的 `question-sql.sql` 可用于：

- 初始化表结构
- 导入基础数据
- 辅助本地联调

实际使用前建议先检查脚本内容是否符合当前数据库环境。

## 推荐阅读顺序

如果你是第一次接触这个项目，建议按下面顺序阅读：

1. `pom.xml`
2. `question-web/src/main/java/.../controller`
3. `question-service/src/main/java/.../service/impl`
4. `question-dao/src/main/java/.../mapper`
5. `question-entity/src/main/java/...`
6. `question-common/src/main/java/...`

## 文档补充

项目目录说明见：

- `PROJECT_STRUCTURE.md`

如果后续需要，我还可以继续帮你补充：

- 接口清单文档
- 部署文档
- 开发规范文档
- Git 提交流程说明
