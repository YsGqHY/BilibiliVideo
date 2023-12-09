package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import taboolib.expansion.getPlayerDataContainer
import java.util.*

/**
 * Mid cache
 * Mid缓存
 */
val midCache = Caffeine.newBuilder()
    .maximumSize(100)
    .build<UUID, String> {
        it.getPlayerDataContainer()["mid"]
    }