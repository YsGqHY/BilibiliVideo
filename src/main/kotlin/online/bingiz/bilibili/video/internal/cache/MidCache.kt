package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import online.bingiz.bilibili.video.internal.database.Database.Companion.getPlayerDataContainer
import java.util.*

/**
 * Mid cache
 * Mid缓存
 */
val midCache = Caffeine.newBuilder()
    .maximumSize(100)
    .build<UUID, String> {
        it.getPlayerDataContainer("mid")
    }