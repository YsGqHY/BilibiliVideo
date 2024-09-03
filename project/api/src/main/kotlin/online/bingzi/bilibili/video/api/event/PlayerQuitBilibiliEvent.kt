package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Player quit bilibili event
 * <p>
 * 玩家退出Bilibili事件
 *
 * @property player 玩家
 * @constructor Create empty Player quit bilibili event
 */
class PlayerQuitBilibiliEvent(val player: Player) : BukkitProxyEvent()
