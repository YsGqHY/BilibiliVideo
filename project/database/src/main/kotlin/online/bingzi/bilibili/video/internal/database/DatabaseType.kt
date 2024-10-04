package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig.config
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import taboolib.common.platform.function.console
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendWarn
import taboolib.platform.util.bukkitPlugin
import java.io.File

enum class DatabaseType {
    MYSQL,
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