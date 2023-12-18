package online.bingiz.bilibili.video.internal.database

import online.bingiz.bilibili.video.internal.helper.infoMessageAsLang
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer
import taboolib.module.database.Table
import java.util.*
import javax.sql.DataSource

interface Database {
    val table: Table<*, *>
    val dataSource: DataSource

    companion object {
        private val INSTANCE by lazy {
            infoMessageAsLang("Database")
            val database = when (DatabaseType.INSTANCE) {
                DatabaseType.SQLITE -> DatabaseSQLite()
                DatabaseType.MYSQL -> DatabaseMySQL()
            }
            infoMessageAsLang("Databased")
            database
        }
        private val tableInstance by lazy {
            INSTANCE.table
        }
        private val dataSourceInstance by lazy {
            INSTANCE.dataSource
        }

        /**
         * Search player by mid
         * 通过mid搜索玩家
         *
         * @param mid mid
         * @return player name
         */
        fun searchPlayerByMid(player: ProxyPlayer, mid: String): Boolean {
            return tableInstance.select(dataSourceInstance) {
                where {
                    "key" eq "mid"
                    "value" eq mid
                }
            }.firstOrNull {
                player.uniqueId.toString() != getString("user")
            } ?: false
        }

        fun UUID.getPlayerDataContainer(key: String): String? {
            return tableInstance.select(dataSourceInstance) {
                where {
                    "user" eq this@getPlayerDataContainer.toString()
                    "key" eq key
                }
            }.firstOrNull {
                getString("value")
            }
        }

        fun UUID.setPlayerDataContainer(key: String, value: String) {
            val find = tableInstance.find(dataSourceInstance) {
                where {
                    "user" eq this@setPlayerDataContainer.toString()
                    "key" eq key
                }
            }
            if (find) {
                tableInstance.update(dataSourceInstance) {
                    where {
                        "user" eq this@setPlayerDataContainer.toString()
                        "key" eq key
                    }
                    set("value", value)
                }
            } else {
                tableInstance.insert(dataSourceInstance, "user", "key", "value") {
                    value(this@setPlayerDataContainer.toString(), key, value)
                }
            }
        }

        fun Player.getDataContainer(key: String): String? {
            return this.uniqueId.getPlayerDataContainer(key)
        }

        fun Player.setDataContainer(key: String, value: String) {
            this.uniqueId.setPlayerDataContainer(key, value)
        }

        fun ProxyPlayer.getDataContainer(key: String): String? {
            return this.uniqueId.getPlayerDataContainer(key)
        }

        fun ProxyPlayer.setDataContainer(key: String, value: String) {
            this.uniqueId.setPlayerDataContainer(key, value)
        }
    }
}