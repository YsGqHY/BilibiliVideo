dependencies {
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    // SQLite驱动
    compileOnly("org.xerial:sqlite-jdbc:3.46.1.3")
    // MySQL驱动
    compileOnly("com.mysql:mysql-connector-j:8.0.33")
    // HikariCP 数据库连接池
    compileOnly("com.zaxxer:HikariCP:5.1.0")

    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:v12004:universal")
    compileOnly(project(":project:core"))
}

// 子模块
taboolib { subproject = true }
