package online.bingzi.bilibili.video.internal.database

import online.bingzi.bilibili.video.internal.config.DatabaseConfig
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.platform.util.bukkitPlugin
import java.io.File

@RuntimeDependencies(
    RuntimeDependency(value = "org.hibernate.orm:hibernate-core:6.6.1.Final", test = "org.hibernate.SessionFactory"),
    RuntimeDependency(value = "com.zaxxer:HikariCP:5.0.1", test = "com.zaxxer.hikari.hibernate.HikariConnectionProvider"),
    RuntimeDependency(value = "org.jetbrains.kotlin:kotlin-reflect:1.5.31"),
    RuntimeDependency(value = "javax.persistence:javax.persistence-api:2.2", test = "javax.persistence.Id"),
    RuntimeDependency(value = "mysql:mysql-connector-java:8.0.33", test = "com.mysql.cj.jdbc.Driver"),
    RuntimeDependency(value = "org.xerial:sqlite-jdbc:3.46.1.1", test = "org.sqlite.JDBC")
)
object Database {
    private val dataFile = File(bukkitPlugin.dataFolder, "data.db")

    // 创建SessionFactory
    val sessionFactory: SessionFactory = Configuration().apply {
        when (DatabaseType.INSTANCE) {
            DatabaseType.MYSQL -> {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                setProperty("hibernate.connection.url", DatabaseConfig.mysqlJdbcUrl)
                setProperty("hibernate.connection.username", DatabaseConfig.mysqlUsername)
                setProperty("hibernate.connection.password", DatabaseConfig.mysqlPassword)
            }

            DatabaseType.SQLITE -> {
                setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect")
                setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                setProperty("hibernate.connection.url", "jdbc:sqlite:${dataFile.absolutePath}") // SQLite数据库文件
            }
        }
        setProperty("hibernate.hbm2ddl.auto", "update") // 自动更新数据库结构
        setProperty("hibernate.connection.provider_class", "com.zaxxer.hikari.hibernate.HikariConnectionProvider") // 使用HikariCP连接池
        setProperty("hibernate.connection.autocommit", "true") // 自动提交
        setProperty("show_sql", "true") // 显示SQL语句
    }.buildSessionFactory()
}
