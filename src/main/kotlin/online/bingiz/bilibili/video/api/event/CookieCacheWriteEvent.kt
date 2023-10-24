package online.bingiz.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Cookie cache write event
 * Cookie缓存写入事件
 *
 * @property player 玩家
 * @constructor Create empty Cookie cache write event
 */
class CookieCacheWriteEvent(val player: Player) : BukkitProxyEvent()