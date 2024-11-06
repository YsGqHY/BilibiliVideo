package online.bingzi.bilibili.video.internal.listener

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import online.bingzi.bilibili.video.api.event.BilibiliQRCodeWriteCacheEvent
import taboolib.common.platform.event.SubscribeEvent

object BilibiliListener {
    @SubscribeEvent
    fun onBilibiliQRCodeWriteCacheEvent(event: BilibiliQRCodeWriteCacheEvent) {
        BilibiliVideoNetWorkAPI.requestBilibiliGetCookie(event.qrCodeKey)
    }
}
