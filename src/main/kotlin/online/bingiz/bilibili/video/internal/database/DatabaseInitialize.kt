package online.bingiz.bilibili.video.internal.database

import online.bingiz.bilibili.video.internal.config.DatabaseConfig
import online.bingiz.bilibili.video.internal.helper.infoMessageAsLang
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.expansion.setupPlayerDatabase

/**
 * Database initialize
 * 数据库初始化
 *
 * @constructor Create empty Database initialize
 */
object DatabaseInitialize {
    /**
     * Setup database
     * 设置数据库
     *
     */
    @Awake(LifeCycle.ENABLE)
    fun setupDatabase() {
        infoMessageAsLang("Database")
        when (DatabaseType.INSTANCEOF) {
            DatabaseType.SQLITE -> {
                setupPlayerDatabase()
            }

            DatabaseType.MYSQL -> {
                setupPlayerDatabase(DatabaseConfig.config)
            }
        }
        infoMessageAsLang("Databased")
    }
}