package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.RemovalCause
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    .removalListener<UUID, List<String>> { key, value, cause ->
        if (cause == RemovalCause.EXPLICIT && key != null) {
            key.getPlayerDataContainer()["cookie"] = gson.toJson(value)
        }
    }
    .build<UUID, List<String>> {
        it.getPlayerDataContainer()["cookie"]?.let { json ->
            gson.fromJson(json, listStringType)
        }
    }