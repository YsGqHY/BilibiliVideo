package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig
import online.bingzi.bilibili.video.internal.config.DatabaseConfig.host
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table

class DatabaseMySQL : Database {
    override val dataSource = host.createDataSource()
    override val table = Table(DatabaseConfig.table, host) {
        add { id() }
        add("user") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("key") {
            type(ColumnTypeSQL.VARCHAR, 64)
        }
        add("value") {
            type(ColumnTypeSQL.VARCHAR, 256)
        }
    }

    init {
        table.workspace(dataSource) { createTable() }.run()
    }
}
