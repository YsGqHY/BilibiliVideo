package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Player login bilibili timeout event
 * <p>
 * 玩家登录Bilibili超时事件
 *
 * @author BingZi-233
 * @since 2.0.0
 * @property player 玩家
 * @constructor Create empty Player login bilibili timeout event
 */
class PlayerLoginBilibiliTimeoutEvent(val player: Player) : BukkitProxyEvent()
