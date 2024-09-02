@file:Suppress("PropertyName", "SpellCheckingInspection")

import io.izzel.taboolib.gradle.BUKKIT_ALL
import io.izzel.taboolib.gradle.KETHER
import io.izzel.taboolib.gradle.METRICS
import io.izzel.taboolib.gradle.UNIVERSAL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import tech.argonariod.gradle.jimmer.JimmerLanguage

plugins {
    java
    id("io.izzel.taboolib") version "2.0.11"
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("tech.argonariod.gradle-plugin-jimmer") version "latest.release"
    id("com.google.devtools.ksp") version "1.8.22-1.0.11"
}

jimmer {
    version.set("latest.release")
    // 将 Jimmer 的 ORM 依赖项设置为 compileOnly 而不是 implementation。
    ormCompileOnly.set(true)
    // 设置语言为Kotlin
    language.set(JimmerLanguage.KOTLIN)
    // 客户端设置
    client {
        // 抑制当编译器版本低于 11 时导致生成错误的客户端代码时抛出的错误。
        ignoreJdkWarning.set(true)
    }
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // TabooLib 配置
    taboolib {
        env {
            install(KETHER)
            install(METRICS)
            install(UNIVERSAL, BUKKIT_ALL)
        }
        version { taboolib = "6.1.2-beta12" }
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
