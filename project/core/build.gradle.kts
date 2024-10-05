dependencies {
    // 引入 内部依赖
    compileOnly(project(":project:nms"))
    // 引入 API依赖
    compileOnly(project(":project:api"))
    // 引入 Database依赖
    compileOnly(project(":project:database"))
}

// 子模块
taboolib {
    subproject = true
}
