package online.bingzi.bilibili.video.internal.database

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.DataSourceConnectionSource
import com.j256.ormlite.jdbc.db.SqliteDatabaseType
import com.j256.ormlite.table.TableUtils
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import online.bingzi.bilibili.video.internal.entity.BindEntity
import taboolib.common.LifeCycle
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Awake
import taboolib.common.platform.function.info
import taboolib.common.platform.function.submit
import taboolib.platform.util.bukkitPlugin
import java.io.File
import java.util.*

@RuntimeDependencies(
    RuntimeDependency(value = "com.j256.ormlite:ormlite-core:6.1"),
    RuntimeDependency(value = "com.j256.ormlite:ormlite-jdbc:6.1"),
    RuntimeDependency(value = "org.xerial:sqlite-jdbc:3.46.1.3"),
    RuntimeDependency(value = "com.zaxxer:HikariCP:5.1.0"),
)
object Database {
    private val file = File(bukkitPlugin.dataFolder, "database.db")
    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = "jdbc:sqlite:${file.absolutePath}"
    }
    private val dataSource = HikariDataSource(hikariConfig)
    val connectionSource = DataSourceConnectionSource(dataSource, SqliteDatabaseType())

    @Awake(LifeCycle.ACTIVE)
    fun init() {
        val createDao: Dao<BindEntity, UUID> = DaoManager.createDao(connectionSource, BindEntity::class.java)
        if (createDao.isTableExists.not()) {
            TableUtils.createTable(createDao)
        }
        val bindEntity = BindEntity()
        bindEntity.playerUUID = UUID.randomUUID()
        bindEntity.playerName = "BingZi-233"
        bindEntity.bilibiliMid = "123456789"
        bindEntity.bilibiliName = "BingZi-233"
        info("create: ${bindEntity.create()}")
        info("BindEntity = $bindEntity")
        var queryForAll = createDao.queryForAll()
        info("queryForAll: ${queryForAll.size}")
        queryForAll.forEach { it ->
            info("- $it")
        }
        bindEntity.playerName = "BingZi-233"
        info("update: ${bindEntity.update()}")
        queryForAll = createDao.queryForAll()
        info("queryForAll: ${queryForAll.size}")
        queryForAll.forEach { it ->
            info("- $it")
        }
        submit(delay = 20L) {
            bindEntity.update()
        }
    }
}
