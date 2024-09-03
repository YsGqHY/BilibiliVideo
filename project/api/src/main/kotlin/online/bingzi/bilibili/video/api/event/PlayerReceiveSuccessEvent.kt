package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Player receive success event
 * <p>
 * 玩家领取成功事件
 *
 * @author BingZi-233
 * @since 2.0.0
 * @property player 玩家
 * @property bv BV号
 * @constructor Create empty Player receive success event
 */
class PlayerReceiveSuccessEvent(val player: Player, val bv: String) : BukkitProxyEvent()
