package online.bingzi.bilibili.video.api.event

import online.bingzi.bilibili.video.internal.entity.CookieEntity
import taboolib.common.platform.ProxyPlayer
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili cookie entity create event
 * <p>
 * 创建 bilibili cookie 实体事件
 *
 * @param proxyPlayer [ProxyPlayer]
 * @param cookieEntity [CookieEntity]
 * @constructor Create empty Bilibili cookie entity create event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliCookieEntityCreateEvent(val proxyPlayer: ProxyPlayer, val cookieEntity: CookieEntity) : BukkitProxyEvent()
