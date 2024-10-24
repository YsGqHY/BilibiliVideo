package online.bingzi.bilibili.video.internal.config

import online.bingzi.bilibili.video.internal.entity.HandEnum
import online.bingzi.bilibili.video.internal.indicator.ServerStageIndicator
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendInfo

/**
 * Main config
 * <p>
 * MainConfig对象用于管理主配置文件和调试状态。
 *
 * @constructor Create empty Main config
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object MainConfig {
    /**
     * 配置文件的引用，使用config.yml文件进行配置，并支持自动重载。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Config(value = "config.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    /**
     * 调试状态，指示当前是否处于调试模式。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    var debugStatus: Boolean = false

    /**
     * Github访问代理，用于访问Github资源。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    var githubProxy: String = ""

    /**
     * 地图发送到玩家到那个位置
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    var settingHand = HandEnum.MAIN_HAND

    /**
     * 虚拟包发送方式
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    var settingAsyncSendPacket = false

    /**
     * 加载配置的方法，从配置文件中读取调试状态。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun load() {
        debugStatus = config.getBoolean("debug")
        githubProxy = config.getString("github.proxy") ?: ""
        settingHand = config.getEnum("setting.hand", HandEnum::class.java) ?: HandEnum.MAIN_HAND
        settingAsyncSendPacket = config.getBoolean("setting.asyncSendPacket")
        if (ServerStageIndicator.serverStage == LifeCycle.ACTIVE) {
            console().sendInfo("reloadSuccess", "config.yml")
        }
    }

    /**
     * 注册自动重载的方法，当配置文件发生变化时重新加载配置。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun registerAutoReload() {
        config.onReload { load() }
    }
}

