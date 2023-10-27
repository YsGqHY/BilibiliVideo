package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import taboolib.expansion.getPlayerDataContainer
import java.util.*

/**
 * Bv cache
 * Bv缓存
 */
val bvCache = Caffeine.newBuilder()
    .maximumSize(100)
    .build<Pair<UUID, String>, Boolean> {
        it.first.getPlayerDataContainer()[it.second]?.toBooleanStrictOrNull() ?: false
    }