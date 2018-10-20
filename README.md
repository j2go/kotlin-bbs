# 简介
**kotlin bbs**

使用 layui 的社区模板，springboot 全家桶，kotlin 语言实现，目标是尽量少的代码

*简洁而不简单*

# 安装
1. 环境 jdk8, mysql
2. git clone
3. `./gradlew build`
4. 配置数据库连接(application.properties)
5. `./gradlew bootRun`

# 技术

- Kotlin
- Spring Boot2
- Spring Security
- Freemarker
- Layui.js

# Docker 安装
docker run -d --name kbbs -e mysql_host=xxxx -e mysql_p=xxxx -80:8080 stiangao/kbbs