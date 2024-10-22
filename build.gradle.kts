@file:Suppress("PropertyName", "SpellCheckingInspection")

import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.19"
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("org.jetbrains.dokka") version "1.9.20"
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    // TabooLib 配置
    taboolib {
        description {
            desc("Bilibili视频一键三连奖励系统，目前维护在GitHub上。遇到问题请先在GitHub上提出Issue，遇到长时间无反应请联系[冰子]。")
            contributors {
                name("坏黑")
                name("冰子")
                name("南瓜")
            }
            links {
                name("homepage").url("https://github.com/BingZi-233/BilibiliVideo")
            }
        }
        env {
            install(Basic)
            install(Bukkit)
            install(BukkitNMSUtil)
            install(Kether)
            install(Metrics)
            install(CommandHelper)
        }
        version { taboolib = "6.2.0-beta20" }
    }

    // 全局仓库
    repositories {
        mavenLocal()
        mavenCentral()
    }
    // 全局依赖
    dependencies {
        compileOnly(kotlin("stdlib"))
    }

    // 编译配置
    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
        }
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(file("docs"))
}
