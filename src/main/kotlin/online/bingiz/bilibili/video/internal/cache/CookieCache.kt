package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import online.bingiz.bilibili.video.internal.helper.decompress
import taboolib.expansion.getPlayerDataContainer
import java.util.*

// GSON序列化组件
private val gson: Gson = Gson()

// 泛化抵抗
private val listStringType = object : TypeToken<List<String>>() {}.type

/**
 * Cookie cache
 * Cookie缓存
 */
val cookieCache = Caffeine.newBuilder()
    .maximumSize(100)
    .build<UUID, List<String>> {
        it.getPlayerDataContainer()["cookie"]?.decompress()?.let { json ->
            gson.fromJson(json, listStringType)
        }
    }