package online.bingzi.bilibili.video.api

import kotlinx.coroutines.delay
import online.bingzi.bilibili.video.api.event.BilibiliQRCodeWriteCacheEvent
import online.bingzi.bilibili.video.internal.cache.Cache
import online.bingzi.bilibili.video.internal.entity.ReleasesData
import online.bingzi.bilibili.video.internal.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.pluginVersion
import taboolib.expansion.chain
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
        chain {
            val resultVo = Network.loginService.generate().await()
            if (resultVo.isSuccess()) {
                Cache.qrCodeCache.put(proxyPlayer.uniqueId, resultVo.data.qrcodeKey)
                BilibiliQRCodeWriteCacheEvent(proxyPlayer, resultVo.data.qrcodeKey).call()
            } else {
                proxyPlayer.sendWarn("responseFailed", resultVo.message)
            }
        }
    }

    fun requestBilibiliGetCookie(qrCodeKey: String) {
        chain {
            while (true) {
                val resultVO = Network.loginService.poll(qrCodeKey).await()
                if (resultVO.isSuccess() && (resultVO.data.isLogin() || resultVO.data.isInvalid())) {
                    return@chain
                }
                delay(2000)
            }
        }
    }
}
