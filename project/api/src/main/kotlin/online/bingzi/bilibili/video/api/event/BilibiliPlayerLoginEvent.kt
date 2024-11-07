package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili player login event
 * <p>
 * 玩家登录Bilibili事件
 *
 * @property player [Player]
 * @constructor Create empty Player login bilibili event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliPlayerLoginEvent(val player: Player) : BukkitProxyEvent()
