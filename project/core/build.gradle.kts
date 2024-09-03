dependencies {
    // 外部依赖
    compileOnly("com.google.zxing:core:3.5.2")
    // 引入 内部依赖
    compileOnly(project(":project:nms"))
    compileOnly(project(":project:api"))
    // 引入 服务端核心
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
}

// 子模块
taboolib {
    relocate("com.google.zxing", "online.bingzi.library.com.google.zxing")
    subproject = true
}
