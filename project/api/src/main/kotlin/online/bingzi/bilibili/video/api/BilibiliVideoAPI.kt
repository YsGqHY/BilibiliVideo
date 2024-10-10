package online.bingzi.bilibili.video.api

import online.bingzi.bilibili.video.internal.cache.Cache
import online.bingzi.bilibili.video.internal.entity.BindEntity
import online.bingzi.bilibili.video.internal.entity.CookieEntity
import online.bingzi.bilibili.video.internal.entity.ReceiveEntity
import java.util.*

/**
 * Bilibili video API
 * <p>
 * API 入口
 *
 * @constructor Create empty Bilibili video API
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object BilibiliVideoAPI {
    /**
     * Get player bind entity
     * <p>
     * 获取玩家绑定数据
     *
     * @param playerUUID Player UUID
     * @return [BindEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerBindEntity(playerUUID: UUID): BindEntity? {
        return Cache.bindCache.get(playerUUID)
    }

    /**
     * Get player cookie entity
     * <p>
     * 获取玩家Cookie数据
     *
     * @param playerUUID Player UUID
     * @return [CookieEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerCookieEntity(playerUUID: UUID): CookieEntity? {
        return Cache.cookieCache.get(playerUUID)
    }

    /**
     * Get player receive entity
     * <p>
     * 获取玩家领取数据
     *
     * @param playerUUID Player UUID
     * @return [ReceiveEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerReceiveEntity(playerUUID: UUID): ReceiveEntity? {
        return Cache.receiveCache.get(playerUUID)
    }
}
