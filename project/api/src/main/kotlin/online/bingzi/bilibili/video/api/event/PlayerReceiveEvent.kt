package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Player receive event
 * <p>
 * 玩家领取时间
 *
 * @author BingZi-233
 * @since 2.0.0
 * @property player 玩家
 * @property bv BV号
 * @constructor Create empty Player receive event
 */
class PlayerReceiveEvent(val player: Player, val bv: String) : BukkitProxyEvent()
