package online.bingiz.bilibili.video

import online.bingiz.bilibili.video.internal.helper.infoMessageAsLang
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Platform
import taboolib.common.platform.Plugin
import taboolib.expansion.setupPlayerDatabase
import taboolib.module.metrics.Metrics
import taboolib.platform.util.bukkitPlugin

/**
 * Bilibili video
 * Bilibili 视频
 *
 * @constructor Create empty Bilibili video
 */
@RuntimeDependencies(
    // 二维码生成组件
    RuntimeDependency("com.google.zxing:core:3.5.2"),
    // GSON序列化组件
    RuntimeDependency("com.google.code.gson:gson:2.10.1"),
    // 本地缓存组件
    RuntimeDependency("com.github.ben-manes.caffeine:caffeine:2.9.3"),
)
object BilibiliVideo : Plugin() {
    /**
     * 初始化
     */
    override fun onLoad() {
        infoMessageAsLang("Loading")
        infoMessageAsLang("Loaded")
    }

    /**
     * 启动
     *
     */
    override fun onEnable() {
        infoMessageAsLang("Enabling")
        infoMessageAsLang("Database")
        setupPlayerDatabase()
        infoMessageAsLang("Databased")
        infoMessageAsLang("Metrics")
        Metrics(20132, bukkitPlugin.description.version, Platform.BUKKIT)
        infoMessageAsLang("Metricsed")
        infoMessageAsLang("Enabled")
    }

    /**
     * 关闭
     *
     */
    override fun onDisable() {
        infoMessageAsLang("Disabling")
        infoMessageAsLang("Disabled")
    }
}