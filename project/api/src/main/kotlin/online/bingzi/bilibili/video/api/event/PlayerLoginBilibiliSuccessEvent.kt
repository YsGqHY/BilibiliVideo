package online.bingzi.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Player login bilibili success event
 * <p>
 * 玩家登录Bilibili成功事件
 *
 * @property player 玩家
 * @constructor Create empty Player login bilibili success event
 */
class PlayerLoginBilibiliSuccessEvent(val player: Player) : BukkitProxyEvent()
