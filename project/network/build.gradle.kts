dependencies {
    // cache 模块
    compileOnly(project(":project:cache"))
    compileOnly("com.squareup.retrofit2:retrofit:2.11.0")
    compileOnly("com.squareup.retrofit2:converter-gson:2.11.0")
}

// 子模块
taboolib {
    subproject = true
}
