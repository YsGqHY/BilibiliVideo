package online.bingzi.bilibili.video.api.event

import taboolib.common.platform.ProxyPlayer
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili QR code write cache event
 * <p>
 * QRCode写入缓存事件
 *
 * @property proxyPlayer 玩家
 * @property qrCodeKey 二维码 key
 * @constructor Create empty Bilibili QR code write cache event
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class BilibiliQRCodeWriteCacheEvent(val proxyPlayer: ProxyPlayer, val qrCodeKey: String) : BukkitProxyEvent()
