package online.bingzi.bilibili.video

import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformSide
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.lang.sendInfo
import taboolib.module.metrics.Metrics
import taboolib.platform.util.bukkitPlugin

@RuntimeDependencies(
    RuntimeDependency(value = "com.google.zxing:core:3.5.2", relocate = ["!com.google.zxing", "online.bingzi.library.com.google.zxing"]),
    RuntimeDependency(value = "")
)
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
