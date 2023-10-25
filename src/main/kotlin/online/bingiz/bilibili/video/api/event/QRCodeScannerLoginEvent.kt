package online.bingiz.bilibili.video.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * QRCode scanner login event
 * 扫码登陆事件
 *
 * @property player player
 * @property cookie cookie
 * @constructor Create empty QRCode scanner success event
 */
class QRCodeScannerLoginEvent(val player: Player, val cookie: List<String>) : BukkitProxyEvent()