package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendWarn

object CommandLogin {
    val execute = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            BilibiliVideoNetWorkAPI.requestBilibiliGetQRCodeKey(sender)
        }
        dynamic(comment = "player") {
            suggestPlayers()
            execute<ProxyCommandSender> { sender, context, _ ->
                // 从上下文中获取玩家名称
                val playerName = context["player"]
                // 根据玩家名称获取ProxyPlayer对象，如果未找到则发送警告信息
                val proxyPlayer = getProxyPlayer(playerName) ?: let {
                    sender.sendWarn("playerNotFound", playerName)
                    return@execute
                }
                BilibiliVideoNetWorkAPI.requestBilibiliGetQRCodeKey(proxyPlayer)
            }
        }
    }
}
