dependencies {
    // 引入 API
    compileOnly(project(":project:api"))
    compileOnly(project(":project:core"))
    compileOnly(project(":project:database"))
    // 引入 服务端核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    // ORMLite 核心
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
    // ORMLite JDBC 驱动
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
}

// 子模块
taboolib { subproject = true }
