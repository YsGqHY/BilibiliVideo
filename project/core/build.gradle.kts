dependencies {
    // 引入 NMS 依赖
    compileOnly(project(":project:nms"))
    // 引入 二维码 依赖
    compileOnly("com.google.zxing:core:3.5.2")
    // 引入 Retrofit2 依赖
    compileOnly("com.squareup.retrofit2:retrofit:2.11.0")
    compileOnly("com.squareup.retrofit2:converter-gson:2.11.0")
    // 引入 Caffeine 缓存
    compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.2")
    // ORMLite 核心
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
    // ORMLite JDBC 驱动
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    // SQLite驱动
    compileOnly("org.xerial:sqlite-jdbc:3.46.1.3")
    // MySQL驱动
    compileOnly("com.mysql:mysql-connector-j:8.2.0")
    // HikariCP 数据库连接池
    compileOnly("com.zaxxer:HikariCP:4.0.3")
    // 引入 服务端 核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
}

// 子模块
taboolib {
    subproject = true
}
