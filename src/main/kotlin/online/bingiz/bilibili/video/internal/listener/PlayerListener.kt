package online.bingiz.bilibili.video.internal.listener

import org.bukkit.event.player.PlayerLoginEvent
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
     * On player login event
     * 玩家登录事件
     *
     * @param event PlayerLoginEvent
     */
    @SubscribeEvent
    fun onPlayerLoginEvent(event: PlayerLoginEvent) {

    }

    /**
     * On player quit event
     * 玩家退出事件
     *
     * @param event PlayerQuitEvent
     */
    @SubscribeEvent
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {

    }
}