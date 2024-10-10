package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoAPI
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendInfo
import taboolib.module.lang.sendWarn

/**
 * Command logout
 * <p>
 * 命令·登出
 *
 * @constructor Create empty Command logout
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandLogout {
    var execute = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            BilibiliVideoAPI.getPlayerCookieEntity(sender.uniqueId)?.delete()
            sender.sendInfo("commandLogoutSuccess")
        }
        dynamic(comment = "player", optional = true, permission = "BilibiliVideo.command.logout.player") {
            suggestPlayers()
            execute<ProxyCommandSender> { sender, context, _ ->
                val playerName = context["player"]
                getProxyPlayer(playerName)?.let {
                    BilibiliVideoAPI.getPlayerCookieEntity(it.uniqueId)?.delete()
                    sender.sendInfo("commandLogoutPlayerSuccess", playerName)
                } ?: sender.sendWarn("playerNotFound", playerName)
            }
        }
    }
}
