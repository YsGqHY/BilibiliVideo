package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.RemovalCause
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import online.bingiz.bilibili.video.internal.util.infoMessageAsLang
import org.bukkit.Bukkit
import taboolib.expansion.getDataContainer
import java.util.*

// GSON序列化组件
private val gson: Gson = Gson()

// 泛化抵抗
private val listStringType = TypeToken<List<String>>().type

val cookieCache = Caffeine.newBuilder()
    .maximumSize(100)
    .removalListener<UUID, List<String>> { key, value, cause ->
        if (cause == RemovalCause.EXPLICIT && key != null) {
            Bukkit.getPlayer(key)?.getDataContainer()?.set("cookie", gson.toJson(value))
                ?: infoMessageAsLang("WarnPlayerNotOnline")
        }
    }
    .build<UUID, List<String>> {
        Bukkit.getPlayer(it)?.getDataContainer()?.get("cookie")?.let { json ->
            gson.fromJson(json, listStringType)
        } ?: ?: infoMessageAsLang ("WarnPlayerNotOnline")
    }