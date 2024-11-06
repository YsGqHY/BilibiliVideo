dependencies {
    // 引入 服务端 核心
    compileOnly("ink.ptms.core:v12101:12101:mapped")
    compileOnly("ink.ptms.core:v12101:12101:universal")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
}

// 子模块
taboolib {
    subproject = true
}
