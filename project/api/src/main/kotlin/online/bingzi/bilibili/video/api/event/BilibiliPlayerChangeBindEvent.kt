package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili player change bind event
 * <p>
 * 玩家变更Bilibili事件(玩家换绑账户事件)
 *
 * @property player 玩家
 * @property oldMid 旧MID
 * @property newMid 新MID
 * @constructor Create empty Player change bilibili event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliPlayerChangeBindEvent(val player: Player, val oldMid: String, val newMid: String) : BukkitProxyEvent()
