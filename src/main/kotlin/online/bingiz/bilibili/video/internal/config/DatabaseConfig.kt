package online.bingiz.bilibili.video.internal.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * Database config
 * 数据库配置
 *
 * @constructor Create empty Database config
 */
object DatabaseConfig {
    @Config(value = "database.yml")
    lateinit var config: Configuration
        private set
}