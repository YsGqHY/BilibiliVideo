package online.bingzi.bilibili.video.api

import online.bingzi.bilibili.video.api.event.BilibiliCookieEntityCreateEvent
import online.bingzi.bilibili.video.internal.cache.Cache
import online.bingzi.bilibili.video.internal.entity.BindEntity
import online.bingzi.bilibili.video.internal.entity.CookieEntity
import online.bingzi.bilibili.video.internal.entity.ReceiveEntity
import online.bingzi.bilibili.video.internal.entity.BindLogEntity
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.getProxyPlayer
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
     * Log bind operation
     * <p>
     * 记录绑定操作日志
     *
     * @param playerUUID 玩家UUID
     * @param playerName 玩家名称
     * @param bilibiliMid B站账户MID
     * @param bilibiliName B站账户名称
     * @param operationType 操作类型
     * @param oldBilibiliMid 旧B站账户MID
     * @param oldBilibiliName 旧B站账户名称
     *
     * @author BingZi-233
     * @since 2.0.1
     */
    private fun logBindOperation(
        playerUUID: UUID,
        playerName: String,
        bilibiliMid: String,
        bilibiliName: String,
        operationType: String,
        oldBilibiliMid: String? = null,
        oldBilibiliName: String? = null
    ) {
        val bindLogEntity = BindLogEntity(
            playerUUID = playerUUID,
            playerName = playerName,
            bilibiliMid = bilibiliMid,
            bilibiliName = bilibiliName,
            operationType = operationType,
            oldBilibiliMid = oldBilibiliMid,
            oldBilibiliName = oldBilibiliName
        )
        bindLogEntity.create()
    }

    /**
     * Save or update player bind entity
     * <p>
     * 保存或更新玩家绑定数据
     *
     * @param proxyPlayer [ProxyPlayer]
     * @param bindEntity [BindEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun saveOrUpdatePlayerBindEntity(proxyPlayer: ProxyPlayer, bindEntity: BindEntity) {
        val oldEntity = BindEntity.getDao().queryForId(proxyPlayer.uniqueId)
        if (oldEntity == null) {
            // 新绑定
            bindEntity.create()
            logBindOperation(
                playerUUID = proxyPlayer.uniqueId,
                playerName = proxyPlayer.name,
                bilibiliMid = bindEntity.bilibiliMid!!,
                bilibiliName = bindEntity.bilibiliName!!,
                operationType = "BIND"
            )
        } else {
            // 换绑
            if (oldEntity.bilibiliMid != bindEntity.bilibiliMid) {
                logBindOperation(
                    playerUUID = proxyPlayer.uniqueId,
                    playerName = proxyPlayer.name,
                    bilibiliMid = bindEntity.bilibiliMid!!,
                    bilibiliName = bindEntity.bilibiliName!!,
                    operationType = "CHANGE",
                    oldBilibiliMid = oldEntity.bilibiliMid,
                    oldBilibiliName = oldEntity.bilibiliName
                )
            }
            bindEntity.update()
        }
    }

    /**
     * Delete player bind entity
     * <p>
     * 删除玩家绑定数据
     *
     * @param bindEntity [BindEntity]
     *
     * @author BingZi-233
     * @since 2.0.1
     */
    fun deletePlayerBindEntity(bindEntity: BindEntity) {
        logBindOperation(
            playerUUID = bindEntity.playerUUID!!,
            playerName = bindEntity.playerName!!,
            bilibiliMid = bindEntity.bilibiliMid!!,
            bilibiliName = bindEntity.bilibiliName!!,
            operationType = "UNBIND"
        )
        bindEntity.delete()
    }

    /**
     * Save or update player cookie entity
     * <p>
     * 保存或更新玩家Cookie数据
     *
     * @param proxyPlayer [ProxyPlayer]
     * @param cookieEntity [CookieEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun saveOrUpdatePlayerCookieEntity(proxyPlayer: ProxyPlayer, cookieEntity: CookieEntity) {
        val entity = CookieEntity.getDao().queryForId(proxyPlayer.uniqueId) ?: let {
            cookieEntity.create()
            cookieEntity
        }
        entity.update()
    }

    /**
     * Set player cookie entity
     * <p>
     * 设置玩家Cookie实体
     *
     * @param qrCodeKey 二维码Key
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun setPlayerCookieEntity(qrCodeKey: String): Boolean {
        val cookieList = Cache.loginCookieCache.get(qrCodeKey) { null } ?: return false
        val refreshToken = Cache.loginRefreshTokenCache.get(qrCodeKey) { null } ?: return false
        val proxyPlayer = (Cache.qrCodeCache.get(qrCodeKey) { null })?.let { getProxyPlayer(it) } ?: return false
        val cookieEntity = CookieEntity(proxyPlayer.uniqueId, proxyPlayer.name)
        val sessData = cookieList.first { it.startsWith("SESSDATA") }.split(";", "=", limit = 2)[1]
        cookieEntity.sessData = sessData
        cookieEntity.refreshToken = refreshToken
        saveOrUpdatePlayerCookieEntity(proxyPlayer, cookieEntity)
        BilibiliCookieEntityCreateEvent(proxyPlayer, cookieEntity).call()
        return true
    }

    /**
     * Set player cookie entity
     * <p>
     * 设置玩家Cookie实体
     *
     * @param proxyPlayer 玩家
     * @param qrCodeKey 二维码Key
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun setPlayerCookieEntity(proxyPlayer: ProxyPlayer, qrCodeKey: String): Boolean {
        val cookieList = Cache.loginCookieCache.get(qrCodeKey) { null } ?: return false
        val refreshToken = Cache.loginRefreshTokenCache.get(qrCodeKey) { null } ?: return false
        val cookieEntity = CookieEntity(proxyPlayer.uniqueId, proxyPlayer.name)
        val sessData = cookieList.first { it.startsWith("SESSDATA") }.split(";", "=", limit = 2)[1]
        cookieEntity.sessData = sessData
        cookieEntity.refreshToken = refreshToken
        cookieEntity.refresh()
        saveOrUpdatePlayerCookieEntity(proxyPlayer, cookieEntity)
        BilibiliCookieEntityCreateEvent(proxyPlayer, cookieEntity).call()
        return true
    }

    /**
     * Set player cookie entity
     * <p>
     * 设置玩家Cookie实体
     *
     * @param player 玩家
     * @param qrCodeKey 二维码Key
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun setPlayerCookieEntity(player: Player, qrCodeKey: String): Boolean {
        val cookieList = Cache.loginCookieCache.get(qrCodeKey) { null } ?: return false
        val refreshToken = Cache.loginRefreshTokenCache.get(qrCodeKey) { null } ?: return false
        val proxyPlayer = getProxyPlayer(player.uniqueId)!!
        val cookieEntity = CookieEntity(player.uniqueId, player.name)
        val sessData = cookieList.first { it.startsWith("SESSDATA") }.split(";", "=", limit = 2)[1]
        cookieEntity.sessData = sessData
        cookieEntity.refreshToken = refreshToken
        saveOrUpdatePlayerCookieEntity(proxyPlayer, cookieEntity)
        BilibiliCookieEntityCreateEvent(proxyPlayer, cookieEntity).call()
        return true
    }

    /**
     * Get player receive entity
     * <p>
     * 获取玩家领取数据
     *
     * @param id Player UUID
     * @return [ReceiveEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerReceiveEntityById(id: UUID): ReceiveEntity? {
        return Cache.receiveCache.get(id)
    }

    /**
     * Get player receive entity by player UUID
     * <p>
     * 通过玩家UUID获取玩家领取数据
     *
     * @param playerUUID Player UUID
     * @return [List]
     * @see [ReceiveEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerReceiveEntityByPlayerUUID(playerUUID: UUID): List<ReceiveEntity> {
        return ReceiveEntity.getDao().queryForMatchingArgs(ReceiveEntity(playerUUID = playerUUID))
    }

    /**
     * Check player receive entity by mid
     * <p>
     * 通过MID检查玩家领取数据
     * true - 已领取
     * false - 未领取
     *
     * @param bilibiliMid Bilibili MID
     * @param bilibiliBv Bilibili BV
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun checkPlayerReceiveEntityByMidAndBv(bilibiliMid: String, bilibiliBv: String): Boolean {
        return ReceiveEntity.getDao().queryForMatchingArgs(ReceiveEntity(bilibiliMid = bilibiliMid, bilibiliBv = bilibiliBv)).isNotEmpty()
    }

    /**
     * Get player receive entity by mid
     * <p>
     * 通过MID获取玩家领取数据
     *
     * @param bilibiliMid Bilibili MID
     * @return [List]
     * @see [ReceiveEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerReceiveEntityByMid(bilibiliMid: String): List<ReceiveEntity> {
        return ReceiveEntity.getDao().queryForMatchingArgs(ReceiveEntity(bilibiliMid = bilibiliMid))
    }

    /**
     * Get player receive entity by bv
     * <p>
     * 通过BV获取玩家领取数据
     *
     * @param bilibiliBv Bilibili BV
     * @return [List]
     * @see [ReceiveEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerReceiveEntityByBv(bilibiliBv: String): List<ReceiveEntity> {
        return ReceiveEntity.getDao().queryForMatchingArgs(ReceiveEntity(bilibiliBv = bilibiliBv))
    }

    /**
     * Get player receive entity by mid and bv
     * <p>
     * 通过MID和BV获取玩家领取数据
     *
     * @param bilibiliMid Bilibili MID
     * @param bilibiliBv Bilibili BV
     * @return [ReceiveEntity]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun getPlayerReceiveEntityByMidAndBv(bilibiliMid: String, bilibiliBv: String): ReceiveEntity? {
        return ReceiveEntity.getDao().queryForMatchingArgs(ReceiveEntity(bilibiliMid = bilibiliMid, bilibiliBv = bilibiliBv)).firstOrNull()
    }

    /**
     * Set player receive entity by player UUID and bv
     * <p>
     * 通过玩家UUID和BV设置玩家领取数据
     *
     * @param playerUUID Player UUID
     * @param playerName Player Name
     * @param bilibiliBv Bilibili BV
     * @param bilibiliMid Bilibili MID
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun setPlayerReceiveEntityByPlayerUUIDAndBv(playerUUID: UUID, playerName: String, bilibiliBv: String, bilibiliMid: String) {
        val receiveEntity = ReceiveEntity(
            playerUUID = playerUUID, playerName = playerName, bilibiliBv = bilibiliBv, bilibiliMid = bilibiliMid
        )
        receiveEntity.create()
    }

    /**
     * Check player receive entity by player UUID and bv
     * <p>
     * 通过玩家UUID和BV检查玩家领取数据
     * true - 已领取
     * false - 未领取
     *
     * @param playerUUID Player UUID
     * @param bilibiliBv Bilibili BV
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.1
     */
    fun checkPlayerReceiveEntityByPlayerUUIDAndBv(playerUUID: UUID, bilibiliBv: String): Boolean {
        return ReceiveEntity.getDao().queryForMatchingArgs(ReceiveEntity(playerUUID = playerUUID, bilibiliBv = bilibiliBv)).isNotEmpty()
    }
}
