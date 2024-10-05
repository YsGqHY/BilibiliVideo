package online.bingzi.bilibili.video.internal.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

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
    @Config(value = "database.yml")
    lateinit var config: Configuration
        private set
}
