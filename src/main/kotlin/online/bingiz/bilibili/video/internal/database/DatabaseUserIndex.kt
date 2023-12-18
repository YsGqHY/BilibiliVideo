package online.bingiz.bilibili.video.internal.database

import online.bingiz.bilibili.video.internal.config.DatabaseConfig

/**
 * Database user index
 * 数据库用户索引
 *
 * @constructor Create empty Database user index
 */
enum class DatabaseUserIndex {
    /**
     * Uuid
     * Uuid - uuid
     *
     * @constructor Create empty Uuid
     */
    UUID,

    /**
     * Name
     * Name - 名称
     *
     * @constructor Create empty Name
     */
    NAME;

    companion object {
        val INSTANCE: DatabaseUserIndex by lazy {
            try {
                valueOf(DatabaseConfig.config.getString("userIndex")!!.uppercase())
            } catch (ignore: Exception) {
                UUID
            }
        }
    }
}