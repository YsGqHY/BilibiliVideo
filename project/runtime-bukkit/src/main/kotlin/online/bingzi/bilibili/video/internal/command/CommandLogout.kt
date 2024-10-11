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
    /**
     * 执行登出子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            // 获取与发送者唯一 ID 绑定的 Bilibili Cookie 实体并删除
            BilibiliVideoAPI.getPlayerCookieEntity(sender.uniqueId)?.delete()
            // 发送登出成功的信息
            sender.sendInfo("commandLogoutSuccess")
        }
        dynamic(comment = "player", optional = true, permission = "BilibiliVideo.command.logout.player") {
            // 提供玩家名称的建议
            suggestPlayers()
            execute<ProxyCommandSender> { sender, context, _ ->
                // 从上下文中获取玩家名称
                val playerName = context["player"]
                // 获取指定名称的玩家
                getProxyPlayer(playerName)?.let {
                    // 获取与该玩家唯一 ID 绑定的 Bilibili Cookie 实体并删除
                    BilibiliVideoAPI.getPlayerCookieEntity(it.uniqueId)?.delete()
                    // 发送指定玩家登出成功的信息
                    sender.sendInfo("commandLogoutPlayerSuccess", playerName)
                } ?: sender.sendWarn("playerNotFound", playerName) // 玩家未找到，发送警告信息
            }
        }
    }
}
