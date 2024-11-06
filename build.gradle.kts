@file:Suppress("PropertyName", "SpellCheckingInspection")

import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.20"
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.dokka") version "1.9.20"
}

// 全局依赖
dependencies {
    compileOnly(fileTree("libs"))
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    // TabooLib 配置
    taboolib {
        env {
            install(Basic)
            install(Bukkit)
            install(BukkitNMSUtil)
            install(Kether)
            install(Metrics)
            install(CommandHelper)
        }

        version {
            taboolib = "6.2.0-beta33"
        }
    }

    // 全局仓库
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.dmulloy2.net/repository/public/")
    }

    // 全局依赖
    dependencies {
        compileOnly(kotlin("stdlib"))
        compileOnly(fileTree("libs"))
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
