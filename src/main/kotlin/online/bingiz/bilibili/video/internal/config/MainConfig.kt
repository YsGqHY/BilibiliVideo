package online.bingiz.bilibili.video.internal.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * Main config
 * 主配置文件
 *
 * @constructor Create empty Main config
 */
object MainConfig {
    /**
     * Config
     * 配置文件
     */
    @Config(value = "config.yml", autoReload = true)
    lateinit var config: Configuration
        private set
}