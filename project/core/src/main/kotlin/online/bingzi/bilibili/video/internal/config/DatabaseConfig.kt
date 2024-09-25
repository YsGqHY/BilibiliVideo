package online.bingzi.bilibili.video.internal.config

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object DatabaseConfig {
    /**
     * 配置文件的引用，使用config.yml文件进行配置，并支持自动重载。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Config(value = "database.yml")
    lateinit var config: Configuration
        private set

    val databaseType by lazy {
        config.getString("database.type")?.uppercase()
    }

    val mysqlJdbcUrl by lazy {
        "jdbc:mysql://${config.getString("database.host")}:${config.getInt("database.port")}/${config.getString("database.database")}?${
            config.getStringList("database.flag").joinToString("&")
        }"
    }

    val mysqlUsername by lazy {
        config.getString("database.username")
    }

    val mysqlPassword by lazy {
        config.getString("database.password")
    }

    /**
     * 加载配置的方法，从配置文件中读取调试状态。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun load() {
    }

    /**
     * 注册自动重载的方法，当配置文件发生变化时重新加载配置。
     * 在生命周期启用时调用。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun registerAutoReload() {
        config.onReload { load() }
    }
}
