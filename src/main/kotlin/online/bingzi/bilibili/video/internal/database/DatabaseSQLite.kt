package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import taboolib.platform.util.bukkitPlugin
import java.io.File

class DatabaseSQLite : Database {
    private val host = File(bukkitPlugin.dataFolder, "data.db").getHost()
    override val dataSource = host.createDataSource()
    override val table = Table(DatabaseConfig.table, host) {
        add { id() }
        add("user") {
            type(ColumnTypeSQLite.TEXT, 36)
        }
        add("key") {
            type(ColumnTypeSQLite.TEXT, 64)
        }
        add("value") {
            type(ColumnTypeSQLite.TEXT, 256)
        }
    }

    init {
        table.workspace(dataSource) { createTable() }.run()
    }
}
