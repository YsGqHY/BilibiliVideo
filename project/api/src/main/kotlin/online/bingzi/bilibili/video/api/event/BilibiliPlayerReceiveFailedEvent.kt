package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili player receive failed event
 * <p>
 * 玩家领取失败事件
 *
 * @property player [Player]
 * @property bv BV号
 * @constructor Create empty Player receive event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliPlayerReceiveFailedEvent(val player: Player, val bv: String) : BukkitProxyEvent()
