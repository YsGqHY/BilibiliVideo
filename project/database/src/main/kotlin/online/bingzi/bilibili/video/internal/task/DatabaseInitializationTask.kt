package online.bingzi.bilibili.video.internal.task

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.table.TableUtils
import online.bingzi.bilibili.video.internal.database.Database.connectionSource
import online.bingzi.bilibili.video.internal.entity.BindEntity
import online.bingzi.bilibili.video.internal.entity.CookieEntity
import online.bingzi.bilibili.video.internal.entity.ReceiveEntity
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import java.util.*

/**
 * Database initialization task
 * <p>
 * 数据库初始化任务
 *
 * @constructor Create empty Database initialization task
 *
 * @author BingZi-233
 * @since 2.0.0
 */
internal object DatabaseInitializationTask {
    /**
     * Database table initialization task
     * <p>
     * 数据库表初始化任务
     * 用于自动创建数据库表结构
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun databaseTableInitializationTask() {
        val bindEntity: Dao<BindEntity, UUID> = DaoManager.createDao(connectionSource, BindEntity::class.java)
        if (bindEntity.isTableExists.not()) {
            TableUtils.createTable(bindEntity)
        }
        val cookieEntity: Dao<CookieEntity, UUID> = DaoManager.createDao(connectionSource, CookieEntity::class.java)
        if (cookieEntity.isTableExists.not()) {
            TableUtils.createTable(cookieEntity)
        }
        val receiveEntity: Dao<ReceiveEntity, UUID> = DaoManager.createDao(connectionSource, ReceiveEntity::class.java)
        if (receiveEntity.isTableExists.not()) {
            TableUtils.createTable(receiveEntity)
        }
    }
}
