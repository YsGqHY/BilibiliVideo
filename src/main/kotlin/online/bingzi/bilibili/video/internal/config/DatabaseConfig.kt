package online.bingzi.bilibili.video.internal.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.database.HostSQL
import taboolib.module.database.getHost

/**
 * Database config
 * 数据库配置
 *
 * @constructor Create empty Database config
 */
object DatabaseConfig {
    @Config("database.yml")
    lateinit var config: Configuration
        private set
    val host: HostSQL by lazy {
        config.getHost("sql")
    }
    val table: String by lazy {
        config.getString("sql.table") ?: "BilibiliVideo"
    }
}
