package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Player login bilibili event
 * <p>
 * 玩家登录Bilibili事件
 *
 * @property player 玩家
 * @constructor Create empty Player login bilibili event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class PlayerLoginBilibiliEvent(val player: Player) : BukkitProxyEvent()
