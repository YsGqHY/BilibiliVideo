package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import online.bingiz.bilibili.video.internal.database.Database.Companion.getPlayerDataContainer
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Bv cache
 * Bv缓存
 */
val bvCache = Caffeine.newBuilder()
    .maximumSize(100)
    .refreshAfterWrite(5, TimeUnit.MINUTES)
    .build<Pair<UUID, String>, Boolean> {
        it.first.getPlayerDataContainer(it.second)?.toBooleanStrictOrNull() ?: false
    }