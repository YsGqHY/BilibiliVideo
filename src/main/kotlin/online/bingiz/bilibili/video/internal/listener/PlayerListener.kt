package online.bingiz.bilibili.video.internal.listener

import online.bingiz.bilibili.video.internal.cache.cookieCache
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

/**
 * Player listener
 * 玩家时间监听器
 *
 * @constructor Create empty Player listener
 */
object PlayerListener {
    /**
     * On player login event
     * 玩家登录事件
     *
     * @param event PlayerLoginEvent
     */
    @SubscribeEvent
    fun onPlayerLoginEvent(event: PlayerLoginEvent) {
        val player = event.player
        // 载入玩家数据容器
        player.setupDataContainer()
    }

    /**
     * On player quit event
     * 玩家退出事件
     *
     * @param event PlayerQuitEvent
     */
    @SubscribeEvent
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val player = event.player
        // 删除玩家缓存数据
        cookieCache.invalidate(player.uniqueId)
        // 卸载玩家数据容器
        player.releaseDataContainer()
    }
}