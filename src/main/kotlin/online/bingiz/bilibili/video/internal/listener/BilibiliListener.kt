package online.bingiz.bilibili.video.internal.listener

import online.bingiz.bilibili.video.api.event.QRCodeScannerLoginEvent
import taboolib.common.platform.event.SubscribeEvent

object BilibiliListener {
    @SubscribeEvent
    fun onQRCodeScannerLoginEvent(event: QRCodeScannerLoginEvent) {
        val player = event.player
        val cookie = event.cookie

    }
}