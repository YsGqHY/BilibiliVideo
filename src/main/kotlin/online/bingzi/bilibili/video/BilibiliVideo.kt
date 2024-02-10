package online.bingzi.bilibili.video

import online.bingzi.bilibili.video.internal.helper.infoMessageAsLang
import taboolib.common.platform.Platform
import taboolib.common.platform.Plugin
import taboolib.module.metrics.Metrics
import taboolib.platform.util.bukkitPlugin

/**
 * Bilibili video
 * Bilibili 视频
 *
 * @constructor Create empty Bilibili video
 */
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
