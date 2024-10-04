package online.bingzi.bilibili.video.internal.helper

object DatabaseHelper {
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

    fun buildSQLiteJdbcUrl(path: String): String {
        return "jdbc:sqlite:$path"
    }
}