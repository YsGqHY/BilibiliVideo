package online.bingzi.bilibili.video.internal.config

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object MainConfig {
    @Config(value = "config.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    var debugStatus: Boolean = false

    @Awake(LifeCycle.ENABLE)
    fun load() {
        debugStatus = config.getBoolean("debug")
    }

    @Awake(LifeCycle.ENABLE)
    fun registerAutoReload() {

        config.onReload { load() }
    }
}
