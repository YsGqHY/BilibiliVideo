package online.bingzi.bilibili.video.internal.task

import com.j256.ormlite.table.TableUtils
import online.bingzi.bilibili.video.internal.helper.DatabaseHelper
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

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
        val bindEntityDaoSource = DatabaseHelper.bindEntityDaoSource
        if (bindEntityDaoSource.isTableExists.not()) {
            TableUtils.createTable(bindEntityDaoSource)
        }
        val cookieEntityDaoSource = DatabaseHelper.cookieEntityDaoSource
        if (cookieEntityDaoSource.isTableExists.not()) {
            TableUtils.createTable(cookieEntityDaoSource)
        }
        val receiveEntityDaoSource = DatabaseHelper.receiveEntityDaoSource
        if (receiveEntityDaoSource.isTableExists.not()) {
            TableUtils.createTable(receiveEntityDaoSource)
        }
    }
}
