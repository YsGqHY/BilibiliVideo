package online.bingzi.bilibili.video.api

import online.bingzi.bilibili.video.api.event.BilibiliCookieWriteCacheEvent
import online.bingzi.bilibili.video.api.event.BilibiliQRCodeWriteCacheEvent
import online.bingzi.bilibili.video.internal.cache.Cache
import online.bingzi.bilibili.video.internal.config.MainConfig
import online.bingzi.bilibili.video.internal.entity.GenerateData
import online.bingzi.bilibili.video.internal.entity.ReleasesData
import online.bingzi.bilibili.video.internal.entity.ResultVO
import online.bingzi.bilibili.video.internal.helper.ImageHelper
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.network.Network
import org.bukkit.entity.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.pluginVersion
import taboolib.module.lang.sendInfo
import taboolib.module.lang.sendWarn
import taboolib.platform.util.bukkitPlugin

/**
 * Bilibili video network API
 * <p>
 * Bilibili video network API
 *
 * @constructor Create empty Bilibili video network API
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object BilibiliVideoNetWorkAPI {
    /**
     * Request latest version
     * <p>
     * 获取最新版本信息
     *
     * @param proxyCommandSender 执行者
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun requestLatestVersion(proxyCommandSender: ProxyCommandSender) {
        Network.githubService.getLatestRelease().enqueue(object : Callback<ReleasesData> {
            override fun onResponse(call: Call<ReleasesData>, response: Response<ReleasesData>) {
                response.body()?.let {
                    // 去掉 v
                    val remoteVersion = it.tagName.removePrefix("v")
                    proxyCommandSender.sendInfo("commandVersionSuccess", pluginVersion, remoteVersion, bukkitPlugin.description.authors)
                } ?: proxyCommandSender.sendInfo("commandVersionFailed", pluginVersion, bukkitPlugin.description.authors)
            }

            override fun onFailure(call: Call<ReleasesData>, t: Throwable) {
                proxyCommandSender.sendInfo("commandVersionFailed", pluginVersion, bukkitPlugin.description.authors)
            }
        })
    }

    /**
     * Request bilibili get qr code key
     * <p>
     * 获取二维码 key
     *
     * @param proxyPlayer 执行玩家
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun requestBilibiliGetQRCodeKey(proxyPlayer: ProxyPlayer) {
        Network.loginService.generate().enqueue(object : Callback<ResultVO<GenerateData>> {
            override fun onResponse(call: Call<ResultVO<GenerateData>>, response: Response<ResultVO<GenerateData>>) {
                response.body()?.let { resultVo ->
                    if (resultVo.isSuccess()) {
                        Cache.qrCodeCache.put(resultVo.data.qrcodeKey, proxyPlayer.uniqueId)
                        BilibiliQRCodeWriteCacheEvent(proxyPlayer, resultVo.data.qrcodeKey).call()
                        val stringToBufferImage = ImageHelper.stringToBufferImage(resultVo.data.url)
                        if (MainConfig.settingAsyncSendPacket) {
                            BilibiliVideoNMSAPI.sendVirtualMapToPlayerAsync(proxyPlayer.castSafely<Player>()!!, stringToBufferImage, MainConfig.settingHand)
                        } else {
                            BilibiliVideoNMSAPI.sendVirtualMapToPlayer(proxyPlayer.castSafely<Player>()!!, stringToBufferImage, MainConfig.settingHand)
                        }
                    } else {
                        proxyPlayer.sendWarn("responseFailed", resultVo.message)
                    }
                }
            }

            override fun onFailure(call: Call<ResultVO<GenerateData>>, t: Throwable) {
                proxyPlayer.sendWarn("networkError", t.message ?: "")
            }

        })
    }

    /**
     * Request bilibili get cookie
     * <p>
     * 获取Cookie
     *
     * @param qrCodeKey 二维码Key
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun requestBilibiliGetCookie(qrCodeKey: String): Boolean {
        val voResponse = Network.loginService.poll(qrCodeKey).execute()
        if (voResponse.isSuccessful) {
            voResponse.body()?.let { resultVO ->
                if (resultVO.isSuccess() && (resultVO.data.isLogin() || resultVO.data.isInvalid())) {
                    if (resultVO.data.isLogin()) {
                        BilibiliCookieWriteCacheEvent(qrCodeKey).call()
                    }
                    return true
                }
            }
        } else {
            debug("网络故障，错误码 ${voResponse.code()} 错误信息 ${voResponse.message()}")
        }
        return false
    }

    /**
     * Request bilibili get buvid3
     * <p>
     * 获取Buvid3
     *
     * @param qrCodeKey 二维码Key
     * @return [String]
     */
    fun requestBilibiliGetBuvid3(qrCodeKey: String): String {
        val voResponse = Network.buvid3Service.getBuvid3().execute()
        return if (voResponse.isSuccessful) {
            voResponse.body()?.data?.buVid ?: ""
        } else {
            ""
        }
    }
}
