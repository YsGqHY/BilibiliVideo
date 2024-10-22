@file:Suppress("PropertyName", "SpellCheckingInspection")

taboolib {
    description {
        name(rootProject.name)
        contributors {
            name("Bkm016")
            name("BingZi-233")
            name("南瓜")
        }

        dependencies {
            name("ProtocolLib").with("bukkit")
            name("PlaceholderAPI").with("bukkit").optional(true)
        }

        links {
            name("homepage").url("https://github.com/BingZi-233/BilibiliVideo")
        }
    }

    relocate("retrofit2", "online.bingzi.bilibili.video.library.retrofit2")
    relocate("com.github.ben-manes", "online.bingzi.bilibili.video.library.caffeine")
    relocate("com.google.zxing", "online.bingzi.bilibili.video.library.zxing")
    relocate("com.j256.ormlite", "online.bingzi.bilibili.video.library.ormlite")
    relocate("com.zaxxer.hikari", "online.bingzi.bilibili.video.library.hikari")
    relocate("org.sqlite", "online.bingzi.bilibili.video.library.sqlite")
    relocate("org.mysql", "online.bingzi.bilibili.video.library.mysql")
}

dependencies {
    taboo("com.squareup.retrofit2:retrofit:2.11.0")
    taboo("com.squareup.retrofit2:converter-gson:2.11.0")
}

tasks {
    jar {
        // 构件名
        archiveBaseName.set(rootProject.name)
        // 打包子项目源代码
        rootProject.subprojects.forEach { from(it.sourceSets["main"].output) }
    }
    sourcesJar {
        // 构件名
        archiveBaseName.set(rootProject.name)
        // 打包子项目源代码
        rootProject.subprojects.forEach { from(it.sourceSets["main"].allSource) }
    }
}
