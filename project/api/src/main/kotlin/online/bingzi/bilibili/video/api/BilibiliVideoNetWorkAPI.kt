package online.bingzi.bilibili.video.api

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


object BilibiliVideoNetWorkAPI {
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

    fun requestBilibiliGetQRCodeKey(proxyPlayer: ProxyPlayer) {
        Network.loginService.generate().enqueue(object : Callback<ResultVO<GenerateData>> {
            override fun onResponse(call: Call<ResultVO<GenerateData>>, response: Response<ResultVO<GenerateData>>) {
                response.body()?.let { resultVo ->
                    if (resultVo.isSuccess()) {
                        Cache.qrCodeCache.put(proxyPlayer.uniqueId, resultVo.data.qrcodeKey)
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

    fun requestBilibiliGetCookie(qrCodeKey: String): Boolean {
        val voResponse = Network.loginService.poll(qrCodeKey).execute()
        if (voResponse.isSuccessful) {
            voResponse.body()?.let { resultVO ->
                if (resultVO.isSuccess() && (resultVO.data.isLogin() || resultVO.data.isInvalid())) {
                    return true
                }
            }
        } else {
            debug("网络故障，错误码 ${voResponse.code()} 错误信息 ${voResponse.message()}")
        }
        return false
    }
}
