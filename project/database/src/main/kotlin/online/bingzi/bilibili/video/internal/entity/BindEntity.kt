package online.bingzi.bilibili.video.internal.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Bind entity
 * <p>
 * 绑定实体
 * 用户记录玩家绑定的Bilibili账户数据
 *
 * @property playerUUID 玩家UUID
 * @property playerName 玩家名称
 * @property bilibiliMid Bilibili账户MID
 * @property bilibiliName Bilibili账户名称
 * @property createTime 创建时间]
 * @constructor Create empty Bind entity
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@Entity
@Table(name = "bilibili_video_bind")
data class BindEntity(
    @Id
    var playerUUID: UUID,
    var playerName: String,
    var bilibiliMid: String,
    var bilibiliName: String,
    var createTime: LocalDateTime
) {
    constructor() : this(UUID.randomUUID(), "", "", "", LocalDateTime.now()) {

    }
}
