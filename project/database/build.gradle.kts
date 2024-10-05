dependencies {
    // ORMLite 核心
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
    // ORMLite JDBC 驱动
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    // SQLite驱动
    compileOnly("org.xerial:sqlite-jdbc:3.46.1.3")
    // MySQL驱动
    compileOnly("com.mysql:mysql-connector-j:8.2.0")
    // HikariCP 数据库连接池
    compileOnly("com.zaxxer:HikariCP:5.1.0")

    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:v12004:universal")
    // 引入 Indicator依赖
    compileOnly(project(":project:indicator"))
}

// 子模块
taboolib {
    subproject = true
    relocate("com.j256.ormlite", "online.bingzi.bilibili.video.library.ormlite")
    relocate("com.zaxxer.hikari", "online.bingzi.bilibili.video.library.hikari")
    relocate("org.sqlite", "online.bingzi.bilibili.video.library.sqlite")
    relocate("org.mysql", "online.bingzi.bilibili.video.library.mysql")
}
