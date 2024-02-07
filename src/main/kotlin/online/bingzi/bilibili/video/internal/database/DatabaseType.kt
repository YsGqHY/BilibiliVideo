package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig

/**
 * Database type
 * 数据库类型
 *
 * @constructor Create empty Database type
 */
enum class DatabaseType {
    /**
     * SQLite
     * SQLite - 本地
     *
     * @constructor Create empty SQLite
     */
    SQLITE,

    /**
     * MySQL
     * MySQL - 远程
     *
     * @constructor Create empty MySQL
     */
    MYSQL;

    companion object {
        val INSTANCE: DatabaseType by lazy {
            try {
                valueOf(DatabaseConfig.config.getString("sql.type")!!.uppercase())
            } catch (ignore: Exception) {
                SQLITE
            }
        }
    }
}
