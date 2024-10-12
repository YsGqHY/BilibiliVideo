package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili player receive success event
 * <p>
 * 玩家领取成功事件
 *
 * @property player 玩家
 * @property bv BV号
 * @constructor Create empty Player receive success event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliPlayerReceiveSuccessEvent(val player: Player, val bv: String) : BukkitProxyEvent()
