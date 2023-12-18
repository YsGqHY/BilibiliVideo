package online.bingiz.bilibili.video.internal.database

import online.bingiz.bilibili.video.internal.config.DatabaseConfig

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
                valueOf(DatabaseConfig.config.getString("type")!!.uppercase())
            } catch (ignore: Exception) {
                SQLITE
            }
        }
    }
}