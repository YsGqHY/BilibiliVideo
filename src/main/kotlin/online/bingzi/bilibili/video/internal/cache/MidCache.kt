package online.bingzi.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import online.bingzi.bilibili.video.internal.database.Database.Companion.getPlayerDataContainer
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Mid cache
 * Mid缓存
 */
val midCache = Caffeine.newBuilder()
    .maximumSize(100)
    .refreshAfterWrite(5, TimeUnit.MINUTES)
    .build<UUID, String> {
        it.getPlayerDataContainer("mid")
    }

/**
 * Uname cache
 * Uname缓存
 */
val unameCache = Caffeine.newBuilder()
    .maximumSize(100)
    .refreshAfterWrite(5, TimeUnit.MINUTES)
    .build<UUID, String> {
        it.getPlayerDataContainer("uname")
    }
