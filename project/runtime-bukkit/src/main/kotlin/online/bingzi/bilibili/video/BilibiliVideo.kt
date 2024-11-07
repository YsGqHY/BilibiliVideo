package online.bingzi.bilibili.video

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformSide
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.lang.sendInfo
import taboolib.module.metrics.Metrics
import taboolib.platform.util.bukkitPlugin

@PlatformSide(Platform.BUKKIT)
object BilibiliVideo : Plugin() {
    /**
     * on load
     * <p>
     * 初始化
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    override fun onLoad() {
        console().sendInfo("loading")
        console().sendInfo("buvid3Loading")
        BilibiliVideoNetWorkAPI.requestBilibiliGetBuvid3WriteCache()
        console().sendInfo("buvid3Loaded")
        console().sendInfo("loaded")
    }

    /**
     * on enable
     * <p>
     * 启动
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    override fun onEnable() {
        console().sendInfo("enabling")
        console().sendInfo("metrics")
        Metrics(20132, bukkitPlugin.description.version, Platform.BUKKIT)
        console().sendInfo("metricated")
        console().sendInfo("enabled")
    }

    /**
     * on disable
     * <p>
     * 关闭
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    override fun onDisable() {
        console().sendInfo("disabling")
        console().sendInfo("disabled")
    }
}
