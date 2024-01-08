package online.bingiz.bilibili.video.internal.expand

import online.bingiz.bilibili.video.internal.cache.midCache
import online.bingiz.bilibili.video.internal.cache.unameCache
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
        return when (args) {
            "uid" -> midCache[player.uniqueId] ?: "N/A-缓存"
            "uname" -> unameCache[player.uniqueId] ?: "N/A-缓存"
            else -> "N/A-未知参数"
        }
    }
}