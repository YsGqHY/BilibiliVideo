package online.bingzi.bilibili.video.internal.entity

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.misc.BaseDaoEnabled
import com.j256.ormlite.table.DatabaseTable
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import java.util.*

/**
 * Cookie entity
 * <p>
 * Cookie实体
 * 用于操作数据库中保存的Cookie数据
 *
 * @property playerUUID 玩家UUID
 * @property playerName 玩家名称
 * @property sessData sessData
 * @property buvid3 buvid3
 * @property expiredTime 数据失效时间
 * @property createTime 创建时间
 * @property updateTime 更新时间
 * @constructor Create empty Cookie entity
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@DatabaseTable(tableName = "bilibili_video_cookie")
data class CookieEntity(
    @DatabaseField(id = true, uniqueIndex = true)
    var playerUUID: UUID? = null,
    @DatabaseField
    var playerName: String? = null,
    @DatabaseField
    var sessData: String? = null,
    // https://github.com/SocialSisterYi/bilibili-API-collect/issues/790
    @DatabaseField
    var buvid3: String? = null,
    @DatabaseField(dataType = DataType.DATE_INTEGER, format = "yyyy-MM-ss HH:mm:ss")
    var expiredTime: Date? = null,
    @DatabaseField(
        dataType = DataType.DATE_STRING,
        format = "yyyy-MM-ss HH:mm:ss",
        columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL",
        readOnly = true,
        canBeNull = false
    )
    var createTime: Date? = null,
    @DatabaseField(version = true, dataType = DataType.DATE_STRING, format = "yyyy-MM-ss HH:mm:ss", canBeNull = false)
    var updateTime: Date? = null
) : BaseDaoEnabled<CookieEntity, UUID>() {
    init {
        dao = DatabaseHelper.cookieEntityDaoSource
    }

    companion object {
        fun getDao(): Dao<CookieEntity, UUID> {
            return DatabaseHelper.cookieEntityDaoSource
        }
    }
}
