package online.bingiz.bilibili.video.internal.listener

import online.bingiz.bilibili.video.internal.cache.baffleCache
import online.bingiz.bilibili.video.internal.cache.bvCache
import online.bingiz.bilibili.video.internal.cache.cookieCache
import online.bingiz.bilibili.video.internal.cache.midCache
import online.bingiz.bilibili.video.internal.database.Database.Companion.setDataContainer
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * Player listener
 * 玩家时间监听器
 *
 * @constructor Create empty Player listener
 */
object PlayerListener {
    /**
     * On player quit event
     * 玩家退出事件
     *
     * @param event PlayerQuitEvent
     */
    @SubscribeEvent
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val player = event.player
        // 清除玩家冷却
        baffleCache.reset(player.name)
        // 保存玩家Cookie数据
        cookieCache.get(player.uniqueId)?.let {
            player.setDataContainer("SESSDATA", it.SESSDATA)
            player.setDataContainer("bili_jct", it.bili_jct)
            player.setDataContainer("DedeUserID", it.DedeUserID)
            player.setDataContainer("DedeUserID__ckMd5", it.DedeUserID__ckMd5)
            player.setDataContainer("sid", it.sid)
        }
        // 驱逐Cookie缓存
        cookieCache.invalidate(player.uniqueId)
        // 驱逐Mid缓存
        midCache.invalidate(player.uniqueId)
        // 驱逐BV缓存
        bvCache.invalidateAll(bvCache.asMap().keys.filter { it.first == player.uniqueId })
    }
}