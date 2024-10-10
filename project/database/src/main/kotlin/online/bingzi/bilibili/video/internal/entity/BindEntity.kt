package online.bingzi.bilibili.video.internal.entity

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.misc.BaseDaoEnabled
import com.j256.ormlite.table.DatabaseTable
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import java.util.*

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
 * @property createTime 创建时间
 * @constructor Create empty Bind entity
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@DatabaseTable(tableName = "bilibili_video_bind")
data class BindEntity(
    @DatabaseField(id = true, uniqueIndex = true)
    var playerUUID: UUID = UUID.randomUUID(),
    @DatabaseField
    var playerName: String = "",
    @DatabaseField
    var bilibiliMid: String = "",
    @DatabaseField
    var bilibiliName: String = "",
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
) : BaseDaoEnabled<BindEntity, UUID>() {
    init {
        dao = DatabaseHelper.bindEntityDaoSource
    }

    companion object {
        fun getDao(): Dao<BindEntity, UUID> {
            return DatabaseHelper.bindEntityDaoSource
        }
    }
}
