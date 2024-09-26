dependencies {
    // Hibernate核心库
    compileOnly("org.hibernate.orm:hibernate-core:6.6.1.Final")
    // HikariCP连接池
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    // MySQL数据库驱动
    compileOnly("mysql:mysql-connector-java:8.0.33")
    // SQLite数据库驱动
    compileOnly("org.xerial:sqlite-jdbc:3.46.1.1")
    // Kotlin反射库
    compileOnly("org.jetbrains.kotlin:kotlin-reflect")
    // JPA API（可选）
    compileOnly("javax.persistence:javax.persistence-api:2.2")

    // 引入 服务端核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")

    compileOnly(project(":project:core"))
}

// 子模块
taboolib { subproject = true }
