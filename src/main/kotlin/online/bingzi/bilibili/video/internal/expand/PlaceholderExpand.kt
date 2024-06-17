package online.bingzi.bilibili.video.internal.expand

import online.bingzi.bilibili.video.internal.cache.bvCache
import online.bingzi.bilibili.video.internal.cache.midCache
import online.bingzi.bilibili.video.internal.cache.unameCache
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

/**
 * Placeholder expand
 * 变量拓展
 *
 * @constructor Create empty Placeholder expand
 */
object PlaceholderExpand : PlaceholderExpansion {
    override val identifier: String
        get() = "BilibiliVideo"

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        if (player == null) {
            return "N/A"
        }
        val argsList: List<String> = args.split("_")
        return when (argsList.size) {
            1 -> when (args) {
                "uid" -> midCache[player.uniqueId] ?: "N/A-缓存"
                "uname" -> unameCache[player.uniqueId] ?: "N/A-缓存"

                else -> "N/A-未知参数"
            }

            2 -> when (argsList[0]) {
                "check" -> (bvCache.get(Pair(player.uniqueId, argsList[1]))?.toString() ?: "false").lowercase()

                else -> "N/A"
            }

            else -> "N/A"
        }
    }
}
