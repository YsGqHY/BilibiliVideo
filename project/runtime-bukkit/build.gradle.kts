dependencies {
    // 引入 API 模块
    compileOnly(project(":project:api"))
    // 引入 核心 模块
    compileOnly(project(":project:core"))
    // 引入 NMS 模块
    compileOnly(project(":project:nms"))
    // ORMLite 核心
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
    // ORMLite JDBC 驱动
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    // 引入 Caffeine 缓存
    compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.2")
    // 引入 服务端 核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
}

// 子模块
taboolib {
    subproject = true
}
