package online.bingiz.bilibili.video.api.event

import taboolib.common.platform.ProxyPlayer
import taboolib.platform.type.BukkitProxyEvent

/**
 * Triple send rewards event
 * 三连奖励发放事件
 *
 * @property player 玩家
 * @property bvid 视频BV号
 * @constructor Create empty Triple send rewards event
 */
class TripleSendRewardsEvent(val player: ProxyPlayer, val bvid: String) : BukkitProxyEvent()