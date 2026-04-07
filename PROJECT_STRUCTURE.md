# 项目目录架构说明

## 1. 项目简介

本项目是一个基于 Maven 的多模块 Java 项目，核心目标是提供 AI 题目生成、题目校验、向量检索、用户权限及基础字典等相关能力。

整体采用分层拆分的方式，将公共能力、数据访问、实体定义、业务逻辑和 Web 接口分别放在独立模块中，便于维护和扩展。

## 2. 根目录结构

```text
lhb-ai-question-agent/
├─ .mvn/                    Maven Wrapper 相关文件
├─ question-common/         公共模块
├─ question-dao/            数据访问层模块
├─ question-entity/         实体与数据传输对象模块
├─ question-service/        业务服务层模块
├─ question-web/            Web 启动与接口模块
├─ .gitignore               Git 忽略配置
├─ pom.xml                  父工程 POM
└─ question-sql.sql         数据库初始化或业务 SQL 脚本
```

## 3. 模块划分说明

### 3.1 `question-common`

公共基础模块，主要存放可被多个模块复用的通用代码。

主要内容：

- `constants/`：系统常量、Redis Key、用户相关常量
- `enums/`：通用枚举定义，如状态、错误码、设备类型等
- `exception/`：业务异常和全局异常处理
- `utils/`：日期、Redis、Spring 上下文等工具类

适合放入这里的内容：

- 与具体业务无强耦合的公共工具
- 全局统一使用的常量、枚举、异常定义

### 3.2 `question-dao`

数据访问层模块，负责与数据库交互。

主要内容：

- `src/main/java/com/lhb/mapper/`：MyBatis Mapper 接口
- `src/main/resources/mappers/`：对应的 MyBatis XML 映射文件

职责说明：

- 负责数据库 CRUD 和复杂 SQL 映射
- 不承担业务编排逻辑

### 3.3 `question-entity`

实体模型模块，负责定义系统中使用的数据结构。

主要内容：

- `dto/`：接口入参或业务传输对象
- `entity/`：数据库实体对象
- `vo/`：接口返回视图对象
- `typeHandlers/`：MyBatis 自定义类型处理器

职责说明：

- 统一管理系统数据模型
- 降低模块之间因重复定义对象带来的维护成本

### 3.4 `question-service`

业务服务层模块，负责核心业务逻辑实现。

主要内容：

- `prompt/`：AI 提示词相关定义
- `service/`：服务接口
- `service/impl/`：服务实现类
- `utils/`：业务层使用的辅助工具
- `App.java`：模块启动或测试入口类

职责说明：

- 编排 DAO、实体、AI 能力和业务规则
- 承担题目生成、题目向量处理、知识点处理、用户业务处理等核心逻辑

### 3.5 `question-web`

Web 接口与应用启动模块，是项目对外提供 HTTP 服务的入口。

主要内容：

- `QuestionWebApplication.java`：Spring Boot 启动类
- `advisor/`：日志增强、统一切面或拦截增强
- `auth/`：权限校验相关逻辑
- `chatmemory/`：聊天记忆存储相关处理
- `config/`：Spring、MyBatis、Redis、AI、Sa-Token 等配置类
- `controller/`：对外暴露的 REST 接口
- `tools/`：题目校验、去重、向量搜索等工具能力
- `src/main/resources/`：应用配置文件和资源文件

职责说明：

- 接收前端或外部系统请求
- 调用 `question-service` 完成业务处理
- 统一承载启动配置、接口暴露和框架集成

## 4. 依赖关系建议理解

从职责分层上，项目可以理解为以下依赖方向：

```text
question-web
    ↓
question-service
    ↓
question-dao
    ↓
question-entity

question-common
    └─ 被多个模块复用
```

说明：

- `question-web` 负责对外提供接口
- `question-service` 负责业务编排
- `question-dao` 负责数据库访问
- `question-entity` 负责统一数据模型
- `question-common` 为公共支撑模块

## 5. 关键文件说明

- `pom.xml`
  父工程配置文件，统一管理模块、依赖版本和构建行为。

- `question-sql.sql`
  项目数据库相关脚本，通常用于初始化表结构、测试数据或业务 SQL。

- `question-web/src/main/resources/application.yaml`
  本地运行配置文件，包含数据库、Redis、AI Key 等配置。
  该文件通常不建议直接提交到公开仓库。

- `question-web/src/main/resources/application.example.yaml`
  示例配置文件，供开发者参考本地环境变量和配置项填写方式。

## 6. 开发时的目录关注建议

如果你是新接手这个项目，建议按下面顺序阅读：

1. 先看根目录 `pom.xml`，了解模块划分和依赖关系
2. 再看 `question-web/controller/`，快速理解系统对外提供了哪些能力
3. 再看 `question-service/service/impl/`，梳理核心业务流程
4. 然后看 `question-dao/mapper/` 和 XML，了解数据落库方式
5. 最后结合 `question-entity/` 和 `question-common/` 理解公共模型与基础能力

## 7. 总结

该项目采用典型的多模块分层架构：

- `common` 负责公共能力
- `entity` 负责数据模型
- `dao` 负责数据访问
- `service` 负责业务逻辑
- `web` 负责接口和启动

这种结构适合中大型 Java 后端项目，便于职责隔离、模块复用以及后续扩展维护。
