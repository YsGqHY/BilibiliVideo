package online.bingzi.bilibili.video.api.event

import taboolib.common.platform.ProxyPlayer
import taboolib.platform.type.BukkitProxyEvent

/**
 * Bilibili QR code write cache event
 *
 * @property proxyPlayer
 * @property qrCodeKey
 * @constructor Create empty Bilibili QR code write cache event
 */
class BilibiliQRCodeWriteCacheEvent(val proxyPlayer: ProxyPlayer, val qrCodeKey: String) : BukkitProxyEvent()
