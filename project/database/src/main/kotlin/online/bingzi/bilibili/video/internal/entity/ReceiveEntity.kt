package online.bingzi.bilibili.video.internal.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Receive entity
 * <p>
 * 领取实体
 * 用户记录玩家领取状态
 *
 * @property playerUUID 玩家UUID
 * @property playerName 玩家名称
 * @property bilibiliBv 领取视频BV
 * @property createTime 领取时间
 * @constructor Create empty Receive entity
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@Entity
@Table(name = "bilibili_video_receive")
data class ReceiveEntity(
    @Id
    var playerUUID: UUID,
    var playerName: String,
    var bilibiliBv: String,
    var createTime: LocalDateTime
) {
    constructor() : this(UUID.randomUUID(), "", "", LocalDateTime.now()) {

    }
}
