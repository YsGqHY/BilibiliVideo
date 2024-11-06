package online.bingzi.bilibili.video.internal.listener

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import online.bingzi.bilibili.video.api.event.BilibiliQRCodeWriteCacheEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submitAsync

object BilibiliListener {
    @SubscribeEvent
    fun onBilibiliQRCodeWriteCacheEvent(event: BilibiliQRCodeWriteCacheEvent) {
        submitAsync(delay = 20L, period = 20L) {
            val isCancel = BilibiliVideoNetWorkAPI.requestBilibiliGetCookie(event.qrCodeKey)
            if (isCancel) {
                this.cancel()
            }
        }
    }
}
