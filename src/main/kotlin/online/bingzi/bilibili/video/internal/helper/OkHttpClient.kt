package online.bingzi.bilibili.video.internal.helper

import okhttp3.OkHttpClient
import online.bingzi.bilibili.video.internal.interceptor.ReceivedCookiesInterceptor
import online.bingzi.bilibili.video.internal.interceptor.UserAgentInterceptor
import taboolib.common.platform.function.pluginId
import taboolib.common.platform.function.pluginVersion


internal val client = OkHttpClient.Builder().addInterceptor(ReceivedCookiesInterceptor())
    .addInterceptor(UserAgentInterceptor("MinecraftPlugin $pluginId/$pluginVersion(lhby233@outlook.com)")).build()
