package online.bingzi.bilibili.video.api

import online.bingzi.bilibili.video.internal.entity.ReleasesData
import online.bingzi.bilibili.video.internal.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.function.pluginVersion
import taboolib.module.lang.sendInfo
import taboolib.platform.util.bukkitPlugin


object BilibiliVideoNetWorkAPI {
    fun sendVersion(proxyCommandSender: ProxyCommandSender) {
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
}
