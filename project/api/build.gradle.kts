dependencies {
    // 引入 服务端核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(project(":project:database"))
    compileOnly(project(":project:cache"))
    // 引入 Caffeine缓存
    compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.2")
}

// 子模块
taboolib { subproject = true }
