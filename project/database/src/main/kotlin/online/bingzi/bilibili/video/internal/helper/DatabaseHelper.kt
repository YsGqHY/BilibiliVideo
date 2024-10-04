package online.bingzi.bilibili.video.internal.helper

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
object DatabaseHelper {
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