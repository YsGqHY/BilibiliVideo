package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig.config
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import taboolib.common.platform.function.console
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendWarn
import taboolib.platform.util.bukkitPlugin
import java.io.File

/**
 * Database type
 * <p>
 * 数据库适配类型枚举
 *
 * @constructor Create empty Database type
 *
 * @author BingZi-233
 * @since 2.0.0
 */
enum class DatabaseType {
    /**
     * Mysql
     *
     * @constructor Create empty Mysql
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    MYSQL,

    /**
     * Sqlite
     *
     * @constructor Create empty Sqlite
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    SQLITE;

    companion object {
        private val sqlite by lazy {
            DatabaseHelper.buildSQLiteJdbcUrl(File(bukkitPlugin.dataFolder, "database.db").absolutePath)
        }
        private val mysql by lazy {
            DatabaseHelper.buildMySQLJdbcUrl(
                config.getString("database.url")!!,
                config.getString("database.port")!!,
                config.getString("database.database")!!,
                config.getString("database.username")!!,
                config.getString("database.password")!!,
                config.getStringList("database.flag")
            )
        }
        private val INSTANCEOF by lazy {
            config.getString("database.type")?.uppercase()?.let { valueOf(it) }
        }

        /**
         * Jdbc url
         *
         * @author BingZi-233
         * @since 2.0.0
         */
        val jdbcUrl by lazy {
            when (INSTANCEOF) {
                MYSQL -> mysql
                SQLITE -> sqlite
                null -> {
                    console().sendWarn("databaseTypeError", console().asLangText(SQLITE.name.lowercase()))
                    sqlite
                }
            }
        }
    }
}