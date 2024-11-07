package online.bingzi.bilibili.video.api.event

import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili cookie write cache event
 * <p>
 * Cookie写入缓存事件
 *
 * @property qrCodeKey 二维码 key
 * @constructor Create empty Bilibili cookie write cache event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliCookieWriteCacheEvent(val qrCodeKey: String) : BukkitProxyEvent()
