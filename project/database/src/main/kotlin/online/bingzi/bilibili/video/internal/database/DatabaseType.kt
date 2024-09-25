package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig
import taboolib.common.platform.function.warning

/**
 * Database type
 * 数据库类型
 *
 * @constructor Create empty Database type
 *
 * @author BingZi-233
 * @since 2.0.0
 */
enum class DatabaseType {
    /**
     * SQLite
     * SQLite - 本地
     *
     * @constructor Create empty SQLite
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    SQLITE,

    /**
     * MySQL
     * MySQL - 远程
     *
     * @constructor Create empty MySQL
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    MYSQL;

    companion object {
        /**
         * Instance
         * <p>
         * 获取实例
         *
         * @author BingZi-233
         * @since 2.0.0
         */
        val INSTANCE: DatabaseType by lazy {
            try {
                valueOf(DatabaseConfig.databaseType!!.uppercase())
            } catch (ignore: Exception) {
                warning("数据库类型设置错误，已自动切换为SQLite.")
                SQLITE
            }
        }
    }
}
