package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendWarn

/**
 * Command login
 * <p>
 * 命令·登录
 *
 * @constructor Create empty Command login
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandLogin {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        // 定义一个执行命令的处理器，处理ProxyPlayer类型的发送者
        execute<ProxyPlayer> { sender, _, _ ->
            // 请求获取B站登录二维码密钥
            BilibiliVideoNetWorkAPI.requestBilibiliGetQRCodeKey(sender)
        }
        // 定义一个动态参数，参数名为"player"
        dynamic(comment = "player") {
            // 提供玩家名称的建议
            suggestPlayers()
            // 定义一个执行命令的处理器，处理ProxyCommandSender类型的发送者
            execute<ProxyCommandSender> { sender, context, _ ->
                // 从上下文中获取玩家名称
                val playerName = context["player"]
                // 根据玩家名称获取ProxyPlayer对象，如果未找到则发送警告信息
                val proxyPlayer = getProxyPlayer(playerName) ?: let {
                    sender.sendWarn("playerNotFound", playerName)
                    return@execute
                }
                // 为指定玩家请求获取B站登录二维码密钥
                BilibiliVideoNetWorkAPI.requestBilibiliGetQRCodeKey(proxyPlayer)
            }
        }
    }
}
