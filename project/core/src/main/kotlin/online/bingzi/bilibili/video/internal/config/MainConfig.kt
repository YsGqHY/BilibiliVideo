package online.bingzi.bilibili.video.internal.config

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

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
     * 加载配置的方法，从配置文件中读取调试状态。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun load() {
        debugStatus = config.getBoolean("debug")
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

