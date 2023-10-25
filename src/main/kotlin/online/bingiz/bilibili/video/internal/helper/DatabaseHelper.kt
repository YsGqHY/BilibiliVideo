package online.bingiz.bilibili.video.internal.helper

import taboolib.expansion.playerDatabase


/**
 * Database helper
 * 数据库辅助类
 *
 * @constructor Create empty Database helper
 */
object DatabaseHelper {
    /**
     * Player database table
     * 玩家数据库表
     */
    private val playerDatabaseTable by lazy {
        playerDatabase?.type?.tableVar()
    }
    private val playerDatabaseSource by lazy {
        playerDatabase?.dataSource
    }

    /**
     * Search player by mid
     * 通过mid搜索玩家
     *
     * @param mid mid
     * @return player name
     */
    fun searchPlayerByMid(mid: String): Boolean {
        return playerDatabaseSource?.let {
            playerDatabaseTable?.select(it) {
                where {
                    "key" eq "mid"
                    "value" eq mid
                }
            }?.firstOrNull {
                true
            } ?: false
        } ?: false
    }
}