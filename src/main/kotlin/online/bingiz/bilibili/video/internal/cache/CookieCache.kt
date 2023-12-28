package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import online.bingiz.bilibili.video.internal.database.Database.Companion.getPlayerDataContainer
import online.bingiz.bilibili.video.internal.entity.CookieData
import java.util.*
import java.util.concurrent.TimeUnit

// GSON序列化组件
val gson: Gson = Gson()

// 泛化抵抗
private val listStringType = object : TypeToken<List<String>>() {}.type

/**
 * Cookie cache
 * Cookie缓存
 */
val cookieCache = Caffeine.newBuilder()
    .maximumSize(100)
    .refreshAfterWrite(5, TimeUnit.MINUTES)
    .build<UUID, CookieData> {
        val cookieData = CookieData()
        it.getPlayerDataContainer("SESSDATA")?.let { cookieData.SESSDATA = it }
        it.getPlayerDataContainer("bili_jct")?.let { cookieData.bili_jct = it }
        it.getPlayerDataContainer("DedeUserID")?.let { cookieData.DedeUserID = it }
        it.getPlayerDataContainer("DedeUserID__ckMd5")?.let { cookieData.DedeUserID__ckMd5 = it }
        it.getPlayerDataContainer("sid")?.let { cookieData.sid = it }
        cookieData
    }