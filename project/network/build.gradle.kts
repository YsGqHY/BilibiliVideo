dependencies {
    compileOnly("com.squareup.retrofit2:retrofit:2.11.0")
    compileOnly("com.squareup.retrofit2:converter-gson:2.11.0")
    // 引入 缓存 模块
    compileOnly(project(":project:cache"))
    // 引入 Caffeine 缓存
    compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.2")
}

// 子模块
taboolib {
    subproject = true
}
