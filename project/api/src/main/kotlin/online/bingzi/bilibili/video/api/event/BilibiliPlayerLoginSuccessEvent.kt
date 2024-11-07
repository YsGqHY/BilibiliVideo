package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili player login success event
 * <p>
 * 玩家登录Bilibili成功事件
 *
 * @property player [Player]
 * @constructor Create empty Player login bilibili success event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliPlayerLoginSuccessEvent(val player: Player) : BukkitProxyEvent()
