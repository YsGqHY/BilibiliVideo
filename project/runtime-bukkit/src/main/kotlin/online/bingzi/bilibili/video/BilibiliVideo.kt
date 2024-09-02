package online.bingzi.bilibili.video

import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformSide
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.lang.sendInfo
import taboolib.module.metrics.Metrics
import taboolib.platform.util.bukkitPlugin

/**
 * 这是你的插件在 Bukkit 平台运行的基础
 * 一般情况下你不需要修改这个类
 */
@PlatformSide(Platform.BUKKIT)
object BilibiliVideo : Plugin() {
    /**
     * 初始化
     */
    override fun onLoad() {
        console().sendInfo("loading")
        console().sendInfo("loaded")
    }

    /**
     * 启动
     *
     */
    override fun onEnable() {
        console().sendInfo("enabling")
        console().sendInfo("metrics")
        Metrics(20132, bukkitPlugin.description.version, Platform.BUKKIT)
        console().sendInfo("metricated")
        console().sendInfo("enabled")
    }

    /**
     * 关闭
     *
     */
    override fun onDisable() {
        console().sendInfo("disabling")
        console().sendInfo("disabled")
    }
}
