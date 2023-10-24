package online.bingiz.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.RemovalCause
import taboolib.expansion.getPlayerDataContainer
import java.util.*

/**
 * Bv cache
 * Bv缓存
 */
val bvCache = Caffeine.newBuilder()
    .maximumSize(100)
    .removalListener<Pair<UUID, String>, Boolean> { key, value, cause ->
        if (cause == RemovalCause.EXPLICIT && key != null) {
            key.first.getPlayerDataContainer()[key.second] = value.toString()
        }
    }
    .build<Pair<UUID, String>, Boolean> {
        it.first.getPlayerDataContainer()[it.second]?.toBooleanStrictOrNull() ?: false
    }