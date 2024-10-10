package online.bingzi.bilibili.video.internal.cache

import com.github.benmanes.caffeine.cache.Caffeine
import online.bingzi.bilibili.video.internal.entity.BindEntity
import online.bingzi.bilibili.video.internal.entity.CookieEntity
import online.bingzi.bilibili.video.internal.entity.ReceiveEntity
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import java.util.*

/**
 * Cache
 * <p>
 * 缓存
 *
 * @constructor Create empty Cache
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@RuntimeDependencies(
    RuntimeDependency(value = "!com.github.ben-manes.caffeine:caffeine:2.9.2")
)
object Cache {
    /**
     * Bind cache
     * <p>
     * 绑定缓存
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val bindCache = Caffeine.newBuilder()
        .maximumSize(100)
        .build<UUID, BindEntity> { k ->
            BindEntity.getDao().queryForId(k)
        }

    /**
     * Cookie cache
     * <p>
     * Cookie缓存
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val cookieCache = Caffeine.newBuilder()
        .maximumSize(100)
        .build<UUID, CookieEntity> { k ->
            CookieEntity.getDao().queryForId(k)
        }

    /**
     * Receive cache
     * <p>
     * 领取缓存
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val receiveCache = Caffeine.newBuilder()
        .maximumSize(100)
        .build<UUID, ReceiveEntity> { k ->
            ReceiveEntity.getDao().queryForId(k)
        }
}
