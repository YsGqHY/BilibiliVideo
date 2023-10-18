package online.bingiz.bilibili.video.internal.expand

import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object PlaceholderExpand : PlaceholderExpansion {
    override val identifier: String
        get() = "BilibiliVideo"

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        return "N/A"
    }
}