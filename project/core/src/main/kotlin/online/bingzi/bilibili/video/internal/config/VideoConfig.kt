package online.bingzi.bilibili.video.internal.config

import online.bingzi.bilibili.video.internal.entity.Video
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendInfo

/**
 * Video config
 * <p>
 * VideoConfig对象用于管理视频配置和视频数据。
 *
 * @constructor Create empty Video config
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object VideoConfig {
    /**
     * 配置文件的引用，使用video.yml文件进行配置，并支持自动重载。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Config(value = "video.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    /**
     * 存储视频数据的映射，键为视频标识符，值为Video对象。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    var videoData: Map<String, Video> = mapOf()

    /**
     * 加载视频数据的方法，将配置文件中的视频信息读取到videoData中。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun load() {
        videoData = config.getKeys(false).associateWith {
            Video(it, config.getStringList("$it.command"))
        }
        console().sendInfo("reloadSuccess", "video.yml")
    }

    /**
     * 注册自动重载的方法，当配置文件发生变化时重新加载视频数据。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun registerAutoReload() {
        config.onReload { load() }
    }

    /**
     * 检查给定的视频标识符是否存在于视频数据中。
     *
     * @param bv 视频标识符
     * @return 如果存在则返回true，否则返回false
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun checkVideo(bv: String): Boolean {
        return videoData.containsKey(bv)
    }

    /**
     * 根据视频标识符获取对应的Video对象。
     *
     * @param bv 视频标识符
     * @return 对应的Video对象，如果不存在则返回null
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getVideo(bv: String): Video? {
        return videoData[bv]
    }
}

