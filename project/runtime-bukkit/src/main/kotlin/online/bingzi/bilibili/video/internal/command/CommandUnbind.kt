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
 * Command unbind
 * <p>
 * 命令·解绑
 *
 * @constructor Create empty Command receive
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandUnbind {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        // 定义一个执行命令的处理器，处理ProxyPlayer类型的发送者
        execute<ProxyPlayer> { sender, context, argument ->
            // 获取与发送者唯一ID相关的Bilibili视频绑定实体
            BilibiliVideoAPI.getPlayerBindEntity(sender.uniqueId)?.let {
                // 如果成功获取绑定实体，向发送者发送解绑成功的信息
                sender.sendInfo("commandUnBindSuccess")
            } ?: // 如果未找到绑定实体，向发送者发送解绑失败的警告信息
            sender.sendWarn("commandUnBindFailed")
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
                // 获取与该ProxyPlayer相关的Bilibili视频绑定实体
                BilibiliVideoAPI.getPlayerBindEntity(proxyPlayer.uniqueId)?.let {
                    // 删除绑定实体
                    it.delete()
                    // 向发送者发送解绑特定玩家成功的信息
                    sender.sendInfo("commandUnBindPlayerSuccess", playerName)
                } ?: // 如果未找到绑定实体，向发送者发送解绑特定玩家失败的警告信息
                sender.sendWarn("commandUnBindPlayerFailed", playerName)
            }
        }
    }

}
