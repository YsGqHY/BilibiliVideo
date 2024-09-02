package online.bingzi.bilibili.video.internal.config

import online.bingzi.bilibili.video.internal.entity.Video
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object VideoConfig {
    @Config(value = "video.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    var videoData: Map<String, Video> = mapOf()

    @Awake(LifeCycle.ENABLE)
    fun load() {
        videoData = config.getKeys(false).associateWith {
            Video(
                it,
                config.getStringList("$it.command")
            )
        }
    }

    @Awake(LifeCycle.ENABLE)
    fun registerAutoReload() {
        config.onReload { load() }
    }
}
