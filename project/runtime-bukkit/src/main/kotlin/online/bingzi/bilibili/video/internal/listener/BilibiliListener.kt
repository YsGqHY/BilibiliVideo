package online.bingzi.bilibili.video.internal.listener

import online.bingzi.bilibili.video.api.BilibiliVideoAPI
import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import online.bingzi.bilibili.video.api.event.BilibiliCookieEntityCreateEvent
import online.bingzi.bilibili.video.api.event.BilibiliCookieWriteCacheEvent
import online.bingzi.bilibili.video.api.event.BilibiliQRCodeWriteCacheEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submitAsync

/**
 * Bilibili listener
 * <p>
 * 哔哩哔哩事件
 *
 * @constructor Create empty Bilibili listener
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object BilibiliListener {
    /**
     * On bilibili QR code write cache event
     * <p>
     * 当二维码写入缓存时
     *
     * @param event [BilibiliQRCodeWriteCacheEvent]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @SubscribeEvent
    fun onBilibiliQRCodeWriteCacheEvent(event: BilibiliQRCodeWriteCacheEvent) {
        submitAsync(delay = 20L, period = 20L) {
            val isCancel = BilibiliVideoNetWorkAPI.requestBilibiliGetCookie(event.qrCodeKey)
            if (isCancel) {
                this.cancel()
            }
        }
    }

    /**
     * On bilibili cookie write cache event
     * <p>
     * 当cookie写入缓存时
     *
     * @param event [BilibiliCookieWriteCacheEvent]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @SubscribeEvent
    fun onBilibiliCookieWriteCacheEvent(event: BilibiliCookieWriteCacheEvent) {
        BilibiliVideoAPI.setPlayerCookieEntity(event.qrCodeKey)
    }

    /**
     * On bilibili cookie entity create event
     * <p>
     * 当cookie实体创建时
     *
     * @param event [BilibiliCookieEntityCreateEvent]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @SubscribeEvent
    fun onBilibiliCookieEntityCreateEvent(event: BilibiliCookieEntityCreateEvent) {

    }
}
