package online.bingzi.bilibili.video.internal.entity

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.misc.BaseDaoEnabled
import com.j256.ormlite.table.DatabaseTable
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import java.util.*

/**
 * Bind log entity
 * <p>
 * 绑定日志实体
 *
 * @property id 日志ID
 * @property playerUUID 玩家UUID
 * @property playerName 玩家名称
 * @property bilibiliMid B站账户MID
 * @property bilibiliName B站账户名称
 * @property operationType 操作类型(BIND-绑定, CHANGE-换绑, UNBIND-解绑)
 * @property oldBilibiliMid 旧B站账户MID(换绑时记录)
 * @property oldBilibiliName 旧B站账户名称(换绑时记录)
 * @property createTime 创建时间
 * @constructor Create empty Bind log entity
 *
 * @author BingZi-233
 * @since 2.0.1
 */
@DatabaseTable(tableName = "bilibili_video_bind_log")
data class BindLogEntity(
    @DatabaseField(generatedId = true)
    var id: Int? = null,
    @DatabaseField
    var playerUUID: UUID? = null,
    @DatabaseField
    var playerName: String? = null,
    @DatabaseField
    var bilibiliMid: String? = null,
    @DatabaseField
    var bilibiliName: String? = null,
    @DatabaseField
    var operationType: String? = null,
    @DatabaseField
    var oldBilibiliMid: String? = null,
    @DatabaseField
    var oldBilibiliName: String? = null,
    @DatabaseField(
        dataType = DataType.DATE_STRING,
        format = "yyyy-MM-ss HH:mm:ss",
        columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL",
        readOnly = true,
        canBeNull = false
    )
    var createTime: Date? = null
) : BaseDaoEnabled<BindLogEntity, Int>() {
    init {
        dao = DatabaseHelper.bindLogEntityDaoSource
    }

    companion object {
        fun getDao(): Dao<BindLogEntity, Int> {
            return DatabaseHelper.bindLogEntityDaoSource
        }
    }
} 