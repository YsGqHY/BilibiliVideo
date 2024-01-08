package online.bingiz.bilibili.video.internal.config

import online.bingiz.bilibili.video.internal.cache.baffleCache
import online.bingiz.bilibili.video.internal.helper.debugStatus
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common5.Baffle
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import java.util.concurrent.TimeUnit

object SettingConfig {

    /**
     * Config
     * 配置文件
     */
    @Config(value = "setting.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    var cooldown: Long = 60

    var needFollow: Boolean = false

    @Awake(LifeCycle.ENABLE)
    fun registerAutoReload() {
        config.onReload { reloadAction() }
    }

    @Awake(LifeCycle.ENABLE)
    fun reloadAction() {
        cooldown = config.getLong("cooldown")
        // 驱逐所有缓存
        baffleCache.resetAll()
        // 变更缓存时间
        baffleCache = Baffle.of(cooldown, TimeUnit.SECONDS)
        // 变更是否需要关注
        needFollow = config.getBoolean("needFollow")
        // 调试模式是否开启
        debugStatus = config.getBoolean("debug")
    }
}
