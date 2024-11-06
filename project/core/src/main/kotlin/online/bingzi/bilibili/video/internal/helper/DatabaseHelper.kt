package online.bingzi.bilibili.video.internal.helper

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import online.bingzi.bilibili.video.internal.database.Database
import online.bingzi.bilibili.video.internal.entity.BindEntity
import online.bingzi.bilibili.video.internal.entity.CookieEntity
import online.bingzi.bilibili.video.internal.entity.ReceiveEntity
import java.util.*

/**
 * Database helper
 * <p>
 * 数据库帮助类
 *
 * @constructor Create empty Database helper
 *
 * @author BingZi-233
 * @since 2.0.0
 */
internal object DatabaseHelper {
    /**
     * Bind entity dao source
     * <p>
     * 绑定实体数据源
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val bindEntityDaoSource: Dao<BindEntity, UUID> by lazy {
        DaoManager.createDao(Database.connectionSource, BindEntity::class.java)
    }

    /**
     * Cookie entity dao source
     * <p>
     * Cookie实体数据源
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val cookieEntityDaoSource: Dao<CookieEntity, UUID> by lazy {
        DaoManager.createDao(Database.connectionSource, CookieEntity::class.java)
    }

    /**
     * Receive entity dao source
     * <p>
     * 领取实体数据源
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val receiveEntityDaoSource: Dao<ReceiveEntity, UUID> by lazy {
        DaoManager.createDao(Database.connectionSource, ReceiveEntity::class.java)
    }

    /**
     * Build MySQL jdbc url
     * <p>
     * 构建MySQL JDBC URL
     *
     * @param url url
     * @param port port
     * @param database database
     * @param username username
     * @param password password
     * @param flag flag
     * @return MySQL JDBC URL
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun buildMySQLJdbcUrl(
        url: String,
        port: String,
        database: String,
        username: String,
        password: String,
        flag: List<String>
    ): String {
        val args = flag.joinToString("&")
        return "jdbc:mysql://$url:$port/$database?user=$username&password=$password&$args"
    }

    /**
     * Build SQLite jdbc url
     * <p>
     * 构建 SQLite JDBC URL
     *
     * @param path path
     * @return SQLite JDBC URL
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun buildSQLiteJdbcUrl(path: String): String {
        return "jdbc:sqlite:$path"
    }
}
