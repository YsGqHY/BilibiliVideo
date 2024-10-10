package online.bingzi.bilibili.video.internal.entity

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.misc.BaseDaoEnabled
import com.j256.ormlite.table.DatabaseTable
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import java.util.*

/**
 * Receive entity
 * <p>
 * 领取实体
 * 用户记录玩家领取状态
 *
 * @property playerUUID 玩家UUID
 * @property playerName 玩家名称
 * @property bilibiliMid 领取账户MID
 * @property bilibiliBv 领取视频BV
 * @property createTime 领取时间
 * @constructor Create empty Receive entity
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@DatabaseTable(tableName = "bilibili_video_receive")
data class ReceiveEntity(
    @DatabaseField(id = true, index = true)
    var playerUUID: UUID = UUID.randomUUID(),
    @DatabaseField
    var playerName: String = "",
    @DatabaseField
    var bilibiliMid: String = "",
    @DatabaseField
    var bilibiliBv: String = "",
    @DatabaseField(
        dataType = DataType.DATE_STRING,
        format = "yyyy-MM-ss HH:mm:ss",
        columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL",
        readOnly = true,
        canBeNull = false
    )
    var createTime: Date = Date(),
    @DatabaseField(version = true, dataType = DataType.DATE_STRING, format = "yyyy-MM-ss HH:mm:ss", canBeNull = false)
    var updateTime: Date = Date()
) : BaseDaoEnabled<ReceiveEntity, UUID>() {
    init {
        dao = DatabaseHelper.receiveEntityDaoSource
    }

    companion object {
        fun getDao(): Dao<ReceiveEntity, UUID> {
            return DatabaseHelper.receiveEntityDaoSource
        }
    }
}
