package online.bingzi.bilibili.video.internal.database

import com.j256.ormlite.jdbc.DataSourceConnectionSource
import com.j256.ormlite.jdbc.db.SqliteDatabaseType
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency

/**
 * Database
 * <p>
 * 数据库
 *
 * @constructor Create empty Database
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@RuntimeDependencies(
    RuntimeDependency(value = "!com.j256.ormlite:ormlite-core:6.1"),
    RuntimeDependency(value = "!com.j256.ormlite:ormlite-jdbc:6.1"),
    RuntimeDependency(value = "!org.xerial:sqlite-jdbc:3.46.1.3"),
    RuntimeDependency(value = "!com.zaxxer:HikariCP:5.1.0"),
    RuntimeDependency(value = "!com.mysql:mysql-connector-j:8.2.0"),
)
internal object Database {
    /**
     * Hikari config
     * <p>
     * Hikari配置
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = DatabaseType.jdbcUrl
    }

    /**
     * Data source
     * <p>
     * 数据源
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private val dataSource = HikariDataSource(hikariConfig)

    /**
     * Connection source
     * <p>
     * ORMLite链接源
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    internal val connectionSource = DataSourceConnectionSource(dataSource, SqliteDatabaseType())
}
