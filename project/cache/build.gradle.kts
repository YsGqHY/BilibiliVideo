dependencies {
    // ORMLite 核心
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
    // ORMLite JDBC 驱动
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    // 引入 Caffeine缓存
    compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.2")
    // 引入 服务端核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(project(":project:database"))
}

// 子模块
taboolib {
    subproject = true
    relocate("com.github.ben-manes", "online.bingzi.bilibili.video.library.caffeine")
}
