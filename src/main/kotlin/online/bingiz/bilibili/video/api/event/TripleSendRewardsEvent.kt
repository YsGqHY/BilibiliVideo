package online.bingiz.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * Triple send rewards event
 * 三连奖励发放事件
 *
 * @property player 玩家
 * @property bvid 视频BV号
 * @constructor Create empty Triple send rewards event
 */
class TripleSendRewardsEvent(val player: Player, val bvid: String) : BukkitProxyEvent()