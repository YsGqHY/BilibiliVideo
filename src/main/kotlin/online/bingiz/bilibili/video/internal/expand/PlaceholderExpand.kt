package online.bingiz.bilibili.video.internal.expand

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
        return "N/A"
    }
}