package online.bingzi.bilibili.video.internal.config

import online.bingzi.bilibili.video.internal.indicator.ServerStageIndicator
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendInfo

/**
 * Database config
 * <p>
 * DatabaseConfig对象用于管理数据库链接配置
 *
 * @constructor Create empty Database config
 *
 * @author BingZi-233
 * @since 2.0.0
 */
internal object DatabaseConfig {
    /**
     * 配置文件的引用，使用database.yml文件进行配置。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Config(value = "database.yml", autoReload = true)
    lateinit var config: Configuration
        private set


    /**
     * 加载配置的方法，从配置文件中读取调试状态。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun load() {
        if (ServerStageIndicator.serverStage == LifeCycle.ACTIVE) {
            console().sendInfo("reloadWarn", "database.yml", console().asLangText("databaseReloadWarn"))
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
