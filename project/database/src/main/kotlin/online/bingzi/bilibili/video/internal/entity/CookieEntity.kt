package online.bingzi.bilibili.video.internal.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Cookie entity
 * <p>
 * Cookie实体
 * 用于操作数据库中保存的Cookie数据
 *
 * @property playerUUID 玩家UUID
 * @property sessData sessData
 * @property buvid3 buvid3
 * @property expiredTime 数据失效时间
 * @constructor Create empty Cookie entity
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@Entity
@Table(name = "bilibili_video_cookie")
data class CookieEntity(
    @Id
    var playerUUID: UUID,
    var sessData: String,
    // https://github.com/SocialSisterYi/bilibili-API-collect/issues/790
    var buvid3: String,
    var expiredTime: LocalDateTime
) {
    constructor() : this(UUID.randomUUID(), "", "", LocalDateTime.now()) {

    }
}
